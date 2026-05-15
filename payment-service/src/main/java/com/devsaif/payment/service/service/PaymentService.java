package com.devsaif.payment.service.service;

import com.devsaif.payment.service.domain.PaymentMethod;
import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.BookingDTO;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO userDTO,
                                    BookingDTO bookingDTO,
                                    PaymentMethod paymentMethod) throws StripeException;

    PaymentOrder gePaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentByPaymentId(String paymentId);

    PaymentLink createJazzCashPaymentLink(UserDTO user,
                                          Long amount,
                                          Long orderId);

    String createStripePaymentLink(UserDTO userDTO,
                                   Long amount,
                                   Long orderId) throws StripeException;


}
