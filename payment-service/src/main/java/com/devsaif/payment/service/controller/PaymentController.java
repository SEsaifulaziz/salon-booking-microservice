package com.devsaif.payment.service.controller;


import com.devsaif.payment.service.domain.PaymentMethod;
import com.devsaif.payment.service.model.PaymentOrder;
import com.devsaif.payment.service.payload.dto.BookingDTO;
import com.devsaif.payment.service.payload.dto.UserDTO;
import com.devsaif.payment.service.payload.response.PaymentLinkResponse;
import com.devsaif.payment.service.service.PaymentService;
import com.devsaif.payment.service.service.client.UserFeignClient;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserFeignClient userFeignClient;

    @Value("${payment.stripe.webhook-secret}")
    private String stripeWebhookSecret;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO bookingDTO,
            @RequestParam PaymentMethod paymentMethode,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        PaymentLinkResponse  response = paymentService.createOrder(
                userDTO,
                bookingDTO,
                paymentMethode
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId
    ) throws Exception {

        PaymentOrder paymentOrder = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(paymentOrder);
    }

    @PostMapping("/webhook/stripe")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature
    ) {

       try{

           Event event = Webhook.constructEvent(
                   payload,
                   signature,
                   stripeWebhookSecret
           );

           System.out.println("Stripe webhook verified");
           System.out.println("Event type: " + event.getType());

           return ResponseEntity.ok("Webhook received");

       }catch (SignatureVerificationException e){
           System.out.println("invalid stripe webhook signature");

           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("Invalid webhook signature");

       }catch (Exception e){
           System.out.println("Webhook processing error: " + e.getMessage());

           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("Webhook processing failed");
       }


    }

}
