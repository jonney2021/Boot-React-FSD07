package com.fsd07.springbootlibrary.service;

import com.fsd07.springbootlibrary.dao.PaymentRepository;
import com.fsd07.springbootlibrary.entity.Payment;
import com.fsd07.springbootlibrary.requestmodels.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
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

/**
 * @Author: Yeming Hu
 * @Date: 9/12/2023
 * @Description: com.fsd07.springbootlibrary.service
 * @Version: 1.0
 */

@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.key.secret}") String secretKey){
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    // Create a payment intent for a given payment information request.
    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException{
        // Define the payment method types
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        // Set up parameters for creating the payment intent
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfoRequest.getAmount());
        params.put("currency", paymentInfoRequest.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        // Create and return the PaymentIntent
        return PaymentIntent.create(params);
    }

    // Processes a Stripe payment for a given user's email.
    public ResponseEntity<String> stripePayment(String userEmail) throws Exception{
        // Retrieve payment information for the user
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if(payment == null){
            throw new Exception("Payment information is missing.");
        }
        // Update the payment amount
        payment.setAmount(00.00);
        paymentRepository.save(payment);  // Save the updated payment information
        return new ResponseEntity<>(HttpStatus.OK);  // Return a response with HTTP status OK
    }
}
