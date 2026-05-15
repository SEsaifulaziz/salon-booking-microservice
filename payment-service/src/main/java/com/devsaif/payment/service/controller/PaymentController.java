package com.devsaif.payment.service.controller;


import com.devsaif.payment.service.domain.PaymentMethod;
import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.BookingDTO;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;
import com.devsaif.payment.service.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO bookingDTO,
            @RequestParam PaymentMethod paymentMethode
            ) throws StripeException {
        UserDTO userDTO = new UserDTO();
        userDTO.setFullName("Saifulaziz");
        userDTO.setEmail("saifulazizse@gmail.com");
        userDTO.setId(1L);

        PaymentLinkResponse  response = paymentService.createOrder(userDTO, bookingDTO, paymentMethode);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId
    ) throws Exception {

        PaymentOrder paymentOrder = paymentService.gePaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(paymentOrder);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) throws StripeException {
        PaymentOrder paymentOrder = paymentService.getPaymentByPaymentId(paymentLinkId);

        Boolean response = paymentService.proceedPayment(paymentOrder,
                paymentId,
                paymentLinkId);

        return ResponseEntity.ok(response);
    }
}
