package com.devsaif.payment.service.service;

import com.devsaif.payment.service.domain.PaymentMethod;
import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.BookingDTO;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;


public interface PaymentService {

    PaymentLinkResponse createOrder(
            UserDTO userDTO,
            BookingDTO bookingDTO,
            PaymentMethod paymentMethod
    ) throws Exception;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentByPaymentId(String paymentId);


}
