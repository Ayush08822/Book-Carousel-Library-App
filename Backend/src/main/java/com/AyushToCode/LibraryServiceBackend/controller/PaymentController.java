package com.AyushToCode.LibraryServiceBackend.controller;

import com.AyushToCode.LibraryServiceBackend.requestmodels.PaymentInfoRequest;
import com.AyushToCode.LibraryServiceBackend.service.PaymentService;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/secure")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest) throws Exception{
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentString = paymentIntent.toJson();

        return new ResponseEntity<>(paymentString , HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@AuthenticationPrincipal Jwt jwt)
            throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        if(userEmail == null) throw new Exception("User email not found");
        return paymentService.stripePayment(userEmail);
    }
}
