package com.devsaif.payment.service.service.impl;

import com.devsaif.payment.service.domain.PaymentMethod;
import com.devsaif.payment.service.domain.PaymentOrderStatus;
import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.BookingDTO;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;
import com.devsaif.payment.service.repository.PaymentOrderRepository;
import com.devsaif.payment.service.service.PaymentService;
import com.devsaif.payment.service.service.provider.PaymentProvider;
import com.devsaif.payment.service.service.provider.PaymentProviderFactory;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepo;
    private final PaymentProviderFactory paymentProviderFactory;


    @Override
    public PaymentLinkResponse createOrder(
            UserDTO userDTO,
            BookingDTO bookingDTO,
            PaymentMethod paymentMethod
    ) throws Exception {

        if (userDTO == null) {
            throw new IllegalArgumentException("User information are required");
        }

        if (bookingDTO == null) {
            throw new IllegalArgumentException("Booking information are required");
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method is required");
        }


        PaymentOrder paymentOrder = new PaymentOrder();

        paymentOrder.setAmount((long) bookingDTO.getTotalPrice());
        paymentOrder.setCurrency("PKR");
        paymentOrder.setStatus(PaymentOrderStatus.CREATED);
        paymentOrder.setPaymentMethod(paymentMethod);

        paymentOrder.setUserId(userDTO.getId());
        paymentOrder.setBookingId(bookingDTO.getId());
        paymentOrder.setSalonId(bookingDTO.getSalonId());

        LocalDateTime now = LocalDateTime.now();
        paymentOrder.setCreatedAt(now);
        paymentOrder.setUpdatedAt(now);

        PaymentOrder savedOrder = paymentOrderRepo.save(paymentOrder);

        PaymentProvider paymentProvider =
                paymentProviderFactory.getProvider(paymentMethod);

        try {

            PaymentLinkResponse response =
                    paymentProvider.createPayment(
                            savedOrder,
                            userDTO
                    );
            savedOrder.setStatus(PaymentOrderStatus.PENDING);

            savedOrder.setUpdatedAt(LocalDateTime.now());

            if (response.getPayment_link_id() != null) {
                savedOrder.setPaymentLinkId(
                        response.getPayment_link_id()
                );
            }

            paymentOrderRepo.save(savedOrder);

            return response;

        } catch (Exception e) {

            savedOrder.setStatus(PaymentOrderStatus.FAILED);

            savedOrder.setFailureReason(
                    e.getMessage()
            );

            savedOrder.setUpdatedAt(LocalDateTime.now());
            paymentOrderRepo.save(savedOrder);

            throw e;
        }

    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {

        PaymentOrder paymentOrder =
                paymentOrderRepo.findById(id).orElse(null);

        if(paymentOrder == null){
            throw new Exception("Payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentByPaymentId(String paymentId) {

        return paymentOrderRepo
                .findByPaymentLinkId(paymentId)
                .orElse(null);
    }

    @Override
    public PaymentLink createJazzCashPaymentLink(
            UserDTO user,
            Long amount,
            Long orderId
    ) {
        throw new UnsupportedOperationException(
                "Use PaymentProviderFactory for payment creation"
        );
    }

    @Override
    public String createStripePaymentLink(
            UserDTO userDTO,
            Long amount,
            Long orderId
    ) {
        throw new UnsupportedOperationException(
                "user PaymentProviderFactory for payment creation"
        );
    }

    @Override
    public Boolean proceedPayment(
            PaymentOrder paymentOrder,
            String paymentId,
            String paymentLinkId
    ) {
        throw new UnsupportedOperationException(
                "Payment confirmation will be handled b provider verification"
        );
    }
}
