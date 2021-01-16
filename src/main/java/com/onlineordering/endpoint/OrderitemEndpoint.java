package com.onlineordering.endpoint;

import com.onlineordering.domain.Orderitem;
import com.onlineordering.repository.OrderitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orderitem")
public class OrderitemEndpoint {

    OrderitemRepository orderitemRepository;

    @Autowired
    public OrderitemEndpoint(OrderitemRepository orderitemRepository) {this.orderitemRepository=orderitemRepository;}

    @GetMapping("")
    public List<Orderitem> findAllOrderItems(){
        List<Orderitem> orderitems = orderitemRepository.findAll();
        return orderitems;
    }

    @GetMapping("/{id}")
    public Orderitem findAllOrderitemById(@PathVariable(value = "id") Long orderitemId){
        Orderitem orderitem = orderitemRepository.findById(orderitemId).orElse(null);
        return orderitem;
    }

    @GetMapping("/order/{order_id}")
    public List<Orderitem> findAllOrderitemByOrderId(@PathVariable(value = "order_id") Long orderId){
        List<Orderitem> orderitems = orderitemRepository.findAll().stream().filter(a->a.getOrders().getId()==orderId).collect(Collectors.toList());
        return orderitems;
    }

    @PostMapping("")
    public Map<String,Boolean> createOrderitemFororder(@Valid @RequestBody Orderitem orderitem){
        Map<String, Boolean> response = new HashMap<>();
        try {
            orderitemRepository.save(orderitem);
            response.put("Success:", Boolean.TRUE);
        } catch (Exception e) {
            response.put("Success:", Boolean.FALSE);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String,Boolean> deleteOrderitem(@PathVariable(value = "id") Long orderitemId){
        Orderitem orderitem = orderitemRepository.findById(orderitemId).orElse(null);
        Map<String, Boolean> response = new HashMap<>();
        if (orderitem != null) {
            orderitemRepository.delete(orderitem);
            response.put("deleted", Boolean.TRUE);
        } else
            response.put("order item not found", Boolean.FALSE);
        return response;
    }
	
}
