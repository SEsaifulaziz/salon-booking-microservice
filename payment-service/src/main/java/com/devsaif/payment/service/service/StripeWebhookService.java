package com.devsaif.payment.service.service;

import com.devsaif.payment.service.config.RabbitMQConfig;
import com.devsaif.payment.service.domain.PaymentOrderStatus;
import com.devsaif.payment.service.messaging.PaymentSuccessfulEvent;
import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.repository.PaymentOrderRepository;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StripeWebhookService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void handleEvent(Event event) throws Exception {

        if ("checkout.session.completed".equals(event.getType())) {

            Session session = (Session) event.getDataObjectDeserializer().deserializeUnsafe();

            String paymentOrderId = session.getMetadata().get("payment_order_id");
            if (paymentOrderId == null) {
                throw new IllegalStateException("payment_order_id missing from Stripe session metadata");
            }

            PaymentOrder paymentOrder = paymentOrderRepository.findById(Long.valueOf(paymentOrderId))
                    .orElseThrow(() -> new IllegalStateException("Payment order not found: " + paymentOrderId));

            if (!"paid".equalsIgnoreCase(session.getPaymentStatus())) {
                throw new IllegalStateException("Stripe payment is not completed");
            }

            Long stripeAmount = session.getAmountTotal();
            if (stripeAmount == null || !stripeAmount.equals(paymentOrder.getAmount())) {
                throw new IllegalStateException("Stripe amount does not match PaymentOrder amount");
            }

            String clientReferenceId = session.getClientReferenceId();
            if (clientReferenceId == null || !clientReferenceId.equals(paymentOrder.getId().toString())) {
                throw new IllegalStateException("Stripe client reference id does not match PaymentOrder ID");
            }

            if (paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
                return; // already processed - idempotent
            }

            String paymentIntentId = session.getPaymentIntent();
            paymentOrder.setProviderTransactionId(paymentIntentId);
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrder.setUpdatedAt(LocalDateTime.now());
            paymentOrderRepository.save(paymentOrder);

            String stripeCurrency = session.getCurrency();
            String paymentOrderCurrency = paymentOrder.getCurrency();
            if (stripeCurrency == null || paymentOrderCurrency == null
                    || !stripeCurrency.trim().equalsIgnoreCase(paymentOrderCurrency.trim())) {
                throw new IllegalStateException("Stripe currency does not match PaymentOrder currency");
            }

            PaymentSuccessfulEvent successfulEvent = new PaymentSuccessfulEvent(
                    paymentOrder.getBookingId(),
                    paymentOrder.getId(),
                    paymentOrder.getUserId(),
                    paymentOrder.getSalonId(),
                    paymentOrder.getAmount()
            );

            rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENT_EVENTS_EXCHANGE, "", successfulEvent);
        }
    }
}