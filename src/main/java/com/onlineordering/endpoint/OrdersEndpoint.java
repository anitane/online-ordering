package com.onlineordering.endpoint;

import com.onlineordering.domain.Orderitem;
import com.onlineordering.domain.Orders;
import com.onlineordering.repository.OrdersRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersEndpoint {

    OrdersRepository ordersRepository;

    @Autowired
    public OrdersEndpoint(OrdersRepository ordersRepository) {this.ordersRepository=ordersRepository;}

    @GetMapping("")
    public List<Orders> findAllOrders(){
        List<Orders> orders = ordersRepository.findAll();
        return orders;
    }

    @GetMapping("/{id}")
    public Orders findAllOrdersById(@PathVariable(value = "id") Long orderId){
        Orders orders = ordersRepository.findById(orderId).orElse(null);
        return orders;
    }

    @GetMapping("/customer/{customer_id}")
    public List<Orders> findAllOrdersByCustomerId(@PathVariable(value = "customer_id") Long customerId){
        List<Orders> orders = ordersRepository.findAll().stream().filter(a-> a.getCustomer_order().getId()==customerId).collect(Collectors.toList());
        return orders;
    }

    @PostMapping("")
    public Map<String,Boolean> createOrderForcustomer(@Valid @RequestBody Orders orders){
        Map<String, Boolean> response = new HashMap<>();
        try {
            ordersRepository.save(orders);
            response.put("Success:", Boolean.TRUE);
        } catch (Exception e) {
            response.put("Success:", Boolean.FALSE);
        }
        return response;
    }

    @PutMapping("add/orderitem/{id}")
    public Map<String,Boolean> updateOrderForcustomer(@PathVariable(value = "id") Long orderId,@Valid @RequestBody Orderitem orderitem){
        Orders order = ordersRepository.findById(orderId).orElse(null);
        Map<String, Boolean> response = new HashMap<>();
        List<Orderitem> orderitems = order.getOrderitems();
        orderitems.add(orderitem);
        order.setOrderitems(orderitems);
        if (order!=null) {
            try {
                ordersRepository.save(order);
                response.put("Success:", Boolean.TRUE);
            } catch (Exception e) {
                response.put("Success:", Boolean.FALSE);
            }
        }else {
            response.put("Order not found:", Boolean.FALSE);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String,Boolean> deleteOrder(@PathVariable(value = "id") Long orderId){
        Orders orders = ordersRepository.findById(orderId).orElse(null);
        Map<String, Boolean> response = new HashMap<>();
        if (orders != null) {
            ordersRepository.delete(orders);
            response.put("deleted", Boolean.TRUE);
        } else
            response.put("order not found", Boolean.FALSE);
        return response;
    }
}
