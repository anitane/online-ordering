package com.onlineordering.endpoint;

import com.onlineordering.domain.Customer;
import com.onlineordering.domain.Orders;
import com.onlineordering.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerEndpoint {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerEndpoint(CustomerRepository customerRepository) {this.customerRepository=customerRepository;}

    @GetMapping("")
    public List<Customer> findAllCustomers(){
        return (List<Customer>) customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable(value = "id") Long customerId){
        Customer customer = customerRepository.
                findById(customerId).orElse(null);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("")
    public Map<String,Boolean> createCustomer(@Valid @RequestBody Customer customer){
        Map<String, Boolean> response = new HashMap<>();
        try {
            customerRepository.save(customer);
            response.put("Success:", Boolean.TRUE);
        }
        catch (Exception e){
            response.put("Success:", Boolean.FALSE);
        }
        return response;
    }

    @PutMapping("/addorder/{id}")
    public Map<String,Boolean> updateOrderCustomer(@PathVariable(value = "id") Long customerId,@Valid @RequestBody Orders orders){
        Customer customer=customerRepository.findById(customerId).orElse(null);
        Map<String, Boolean> response = new HashMap<>();
        if (customer != null) {
        List<Orders> ordersList = customer.getOrders();
        for (Orders order: ordersList) {
            if (order.getId() != orders.getId() && order.getStatus() == 0) {
                response.put("Order is still pending to be completed - Please add items to existing order:" + order.getId(), Boolean.FALSE);
                return response;
            }
        }
        ordersList.add(orders);
        customer.setOrders(ordersList);
            try {
                customerRepository.save(customer);
                response.put("Success:", Boolean.TRUE);
            } catch (Exception e) {
                response.put("Success:", Boolean.FALSE);
            }
        }
        else{
            response.put("Customer not found:", Boolean.FALSE);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String,Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId){
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Map<String, Boolean> response = new HashMap<>();
        if (customer!= null){
            customerRepository.delete(customer);
            response.put("deleted",Boolean.TRUE);
        }
        else
            response.put("Customer not found" , Boolean.FALSE);
        return response;
    }
	
}
