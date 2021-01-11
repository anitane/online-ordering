package com.onlineordering.endpoint;

import com.onlineordering.domain.Customer;
import com.onlineordering.domain.Payment;
import com.onlineordering.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentEndpoint {

    PaymentRepository paymentRepository;

    @Autowired
    public PaymentEndpoint(PaymentRepository paymentRepository) {this.paymentRepository=paymentRepository;}
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("")
    public List<Payment> findPayments(){
        List<Payment> payments = paymentRepository.findAll();
        return payments;
    }

    @PostMapping("")
    public Map<String,Boolean> createPaymentForcustomer(@Valid @RequestBody Payment payment){
        Map<String, Boolean> response = new HashMap<>();
            try {
                paymentRepository.save(payment);
                response.put("Success:", Boolean.TRUE);
            } catch (Exception e) {
                response.put("Success:", Boolean.FALSE);
            }
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String,Boolean> deletePayment(@PathVariable(value = "id") Long paymentid){
        Payment payment = paymentRepository.findById(paymentid).orElse(null);
        Map<String, Boolean> response = new HashMap<>();
            if (payment != null) {
                paymentRepository.delete(payment);
                response.put("deleted", Boolean.TRUE);
            } else
                response.put("Customer not found", Boolean.FALSE);
        return response;
    }

}
