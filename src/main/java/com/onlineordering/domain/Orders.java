package com.onlineordering.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" ,nullable = false)
    private long id;

    @Column (name = "amount" ,nullable = false)
    private int amount;

    @Column (name = "status" ,nullable = false)
    private int status;

    @JsonManagedReference
    @OneToMany(mappedBy = "orders" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orderitem> orderitems = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id" , referencedColumnName = "id")
    private Customer customer_order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Orderitem> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<Orderitem> orderitems) {
        this.orderitems = orderitems;
    }

    public Customer getCustomer_order() {
        return customer_order;
    }

    public void setCustomer_order(Customer customer_order) {
        this.customer_order = customer_order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
