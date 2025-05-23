package com.AyushToCode.LibraryServiceBackend.service;

import com.AyushToCode.LibraryServiceBackend.dao.PaymentRepository;
import com.AyushToCode.LibraryServiceBackend.entity.Payment;
import com.AyushToCode.LibraryServiceBackend.requestmodels.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository , @Value("${stripe.key.secret}") String secretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws Exception{
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        paymentMethodTypes.add("ideal");

        Map<String , Object> params = new HashMap<>();
        params.put("amount" , paymentInfoRequest.getAmount());
        params.put("currency" , paymentInfoRequest.getCurrency());
        params.put("payment_method_types" , paymentMethodTypes);

//        When this payment intent is created, Stripe stores the amount and associates it with a unique client_secret
        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception{
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if(payment == null){
            throw new Exception("Payment information is missing");
        }
        payment.setAmount(00.00);
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
