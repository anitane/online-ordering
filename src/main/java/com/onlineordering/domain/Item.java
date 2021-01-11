package com.onlineordering.domain;

import javax.persistence.*;

@Entity
@Table (name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id" ,nullable = false)
    private long id;

    @Column (name = "name" ,nullable = false)
    private String name;

    @Column (name = "description" ,nullable = false)
    private String description;

    @Column (name = "price" ,nullable = false)
    private double price;

    @Column (name = "available" ,nullable = false)
    private int available;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
