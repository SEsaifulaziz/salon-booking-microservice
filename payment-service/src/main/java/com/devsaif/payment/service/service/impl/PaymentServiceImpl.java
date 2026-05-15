package com.devsaif.payment.service.service.impl;

import com.devsaif.payment.service.domain.PaymentMethod;
import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.BookingDTO;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;
import com.devsaif.payment.service.repository.PaymentOrderRepository;
import com.devsaif.payment.service.service.PaymentService;
import com.stripe.model.PaymentLink;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepo;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;



    @Override
    public PaymentLinkResponse createOrder(UserDTO userDTO,
                                           BookingDTO bookingDTO,
                                           PaymentMethod paymentMethod) {

        Long amount = (long) bookingDTO.getTotalPrice();

        PaymentOrder paymentOrder = new PaymentOrder();

        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setBookingId(bookingDTO.getId());
        paymentOrder.setSalonId(bookingDTO.getSalonId());
        PaymentOrder savedOrder = paymentOrderRepo.save(paymentOrder);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

        if(paymentMethod.equals(paymentMethod.JAZZCASH)){
            PaymentLink payment = createJazzCashPaymentLink(userDTO,
                    savedOrder.getAmount(),
                    savedOrder.getId());

            String paymentUrl = payment.getUrl();
            String paymentUrlId = payment.getId();

            paymentLinkResponse.setPayment_link_url(paymentUrl);
            paymentLinkResponse.setPayment_link_id(paymentUrlId);

            savedOrder.setPaymentLinkedId(paymentUrlId);

            paymentOrderRepo.save(savedOrder);

        }else{
            String paymentUrl = createStripePaymentLink(userDTO,
                    savedOrder.getAmount(),
                    savedOrder.getId());
            paymentLinkResponse.setPayment_link_url(paymentUrl);
        }

        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder gePaymentOrderById(Long id) {
        return null;
    }

    @Override
    public PaymentOrder getPaymentByPaymentId(String paymentId) {
        return null;
    }

    @Override
    public PaymentLink createJazzCashPaymentLink(UserDTO user, Long amount, Long orderId) {
        return null;
    }

    @Override
    public String createStripePaymentLink(UserDTO userDTO, Long amount, Long orderId) {
        return "";
    }
}
