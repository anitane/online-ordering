package com.onlineordering.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table (name = "order_item")
public class Orderitem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" ,nullable = false)
    private long id;

    @Column (name = "item_id" ,nullable = false)
    private long item_id;

    @Column (name = "preferences" )
    private String preferences;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id" , referencedColumnName = "id" )
    private Orders orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
