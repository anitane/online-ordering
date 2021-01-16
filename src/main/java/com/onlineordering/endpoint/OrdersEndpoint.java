package com.onlineordering.endpoint;


import com.onlineordering.domain.Orderitem;
import com.onlineordering.domain.Orders;
import com.onlineordering.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        //get all orders for customer - check if order is still opened
        List<Orders> ordersList = ordersRepository.findAll().stream().filter(a-> a.getCustomer_order().getId() == orders.getCustomer_order().getId()).collect(Collectors.toList());
        if (!ordersList.isEmpty()){
            for (Orders order: ordersList) {
                if (order.getId() != orders.getId() && order.getStatus() == 0) {
                    response.put("Order is still pending to be completed - Please add items to existing order:" + order.getId(), Boolean.FALSE);
                    return response;
                }
            }
        }
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
        if (order!=null) {
        List<Orderitem> orderitems = order.getOrderitems();
        orderitems.add(orderitem);
        order.setOrderitems(orderitems);
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

    @PutMapping("update/{id}")
    public Map<String,Boolean> updateOrderStatus(@PathVariable(value = "id") Long orderId,@Valid @RequestBody Orders orders){
        //check if order exist
        Orders order = ordersRepository.findById(orderId).orElse(null);
        Map<String, Boolean> response = new HashMap<>();
        if (order!=null) {
        order.setStatus(orders.getStatus());
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
