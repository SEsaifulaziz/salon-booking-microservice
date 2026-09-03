package com.devsaif.payment.service.service.provider;

import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;

public interface PaymentProvider {

    PaymentLinkResponse createPayment(

            PaymentOrder paymentOrder,
            UserDTO user
    ) throws Exception;
}
