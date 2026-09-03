package com.devsaif.payment.service.service.provider;

import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;
import org.springframework.stereotype.Component;

@Component
public class JazzCashPaymentProvider implements PaymentProvider{

    @Override
    public PaymentLinkResponse createPayment(PaymentOrder paymentOrder, UserDTO user) throws Exception {

        throw new UnsupportedOperationException(
                "Not supported yet."
        );
    }
}
