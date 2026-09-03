package com.devsaif.payment.service.repository;

import com.devsaif.payment.service.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentOrderRepository
        extends JpaRepository<PaymentOrder, Long> {

    Optional<PaymentOrder> findByPaymentLinkId(String paymentLinkId);

    Optional<PaymentOrder> findByProviderTransactionId(
            String providerTransactionId
    );

    Optional<PaymentOrder> findByBookingId(Long bookingId);
}
