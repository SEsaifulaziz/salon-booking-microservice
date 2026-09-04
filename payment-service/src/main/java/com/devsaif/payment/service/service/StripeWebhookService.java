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
                    .getObject()
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Unable to deserialize Stripe Checkout Session"
                            )
                    );

            String paymentOrderId =
                    session.getMetadata().get("payment_order_id");

            if(paymentOrderId == null){
                throw new IllegalStateException(
                        "payment_order_id messing from Stripe session metadata"
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
                    "PaymentOrder found: " +  paymentOrder.getId()
            );
        }
    }
}