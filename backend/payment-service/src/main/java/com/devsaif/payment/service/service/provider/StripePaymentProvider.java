package com.devsaif.payment.service.service.provider;

import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentProvider implements PaymentProvider {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${payment.frontend.success-url}")
    private String successUrl;

    @Value("${payment.frontend.cancel-url}")
    private String cancelUrl;

    @Override
    public PaymentLinkResponse createPayment(PaymentOrder paymentOrder, UserDTO user) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "/" + paymentOrder.getId())
                .setCancelUrl(cancelUrl)

                // Lets us reconcile the Stripe payment
                // with our internal payment order.
                .setClientReferenceId(
                        paymentOrder.getId().toString()
                )

                .putMetadata(
                        "payment_order_id",
                        paymentOrder.getId().toString()
                )

                .putMetadata(
                        "booking_id",
                        paymentOrder.getBookingId().toString()
                )

                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData
                                                .builder()
                                                .setCurrency(
                                                        paymentOrder
                                                                .getCurrency()
                                                                .toLowerCase()
                                                )
                                                .setUnitAmount(
                                                        paymentOrder.getAmount()
                                                )
                                                .setProductData(
                                                        SessionCreateParams
                                                                .LineItem
                                                                .PriceData
                                                                .ProductData
                                                                .builder()
                                                                .setName("Salon Appointment Booking"
                                                                )
                                                                .build()
                                                ).build()
                                ).build()
                )
                .build();
        Session session = Session.create(params);
        PaymentLinkResponse response = new PaymentLinkResponse();
        response.setPayment_link_url(session.getUrl());
        response.setPayment_link_id(session.getId());

        return response;
    }
}
