package com.devsaif.payment.service.service;

import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.repository.PaymentOrderRepository;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StripeWebhookService {

    private final PaymentOrderRepository paymentOrderRepository;

    public void handleEvent(Event event) throws Exception {

        if("checkout.session.completed".equals(event.getType())){

            Session session =  (Session) event.getDataObjectDeserializer()
                    .deserializeUnsafe();

            System.out.println("Stripe Session ID: "  + session.getId());
            System.out.println("Stripe Session metadata: " +  session.getMetadata());
            System.out.println(
                    "Stripe Client Reference ID: "
                    + session.getClientReferenceId()
            );

            String paymentOrderId =
                    session.getMetadata().get("payment_order_id");

            if(paymentOrderId == null){
                throw new IllegalStateException(
                        "payment_order_id missing from Stripe session metadata"
                );
            }

            PaymentOrder paymentOrder =
                    paymentOrderRepository.findById(
                            Long.valueOf(paymentOrderId)
                    ).orElseThrow(() ->
                            new IllegalStateException(
                                    "Payment order not found: " + paymentOrderId
                            )
                    );

            System.out.println(
                    "Stripe payment status: " + session.getPaymentStatus()
            );

            if(!"paid".equalsIgnoreCase(session.getPaymentStatus())){
                throw new IllegalStateException(
                        "Stripe payment is not completed"
                );
            }

            System.out.println(
                    "Stripe payment confirmed as paid"
            );

            System.out.println(
                    "PaymentOrder found: " +  paymentOrder.getId()
            );

            Long stripeAmount = session.getAmountTotal();

            System.out.println(
                    "PaymentOrder amount: " + paymentOrder.getAmount()
            );

            System.out.println(
                    "Stripe amount: " + stripeAmount
            );

            if(stripeAmount == null ||
                !stripeAmount.equals(paymentOrder.getAmount())) {
                throw new IllegalStateException(
                        "Stripe amount does not match PaymentOrder amount"
                );
            }

            System.out.println(
                    "Stripe amount verified"
            );

            String stripeCurrency = session.getCurrency();

            System.out.println(
                    "PaymentOrder currency: " + paymentOrder.getCurrency()
            );

            System.out.println(
                    "Stripe currency: " + stripeCurrency
            );

            if(stripeCurrency == null ||
                    !stripeCurrency.equals(paymentOrder.getCurrency())) {

                throw new IllegalStateException(
                        "Stripe currency does not match PaymentOrder currency"
                );
            }

            System.out.println(
                    "PaymentOrder found: "  +  paymentOrder.getId()
            );
        }
    }
}