package com.triggerise.store.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(name="name")
    private String name;
    @Column(name="code")
    private String code;
    @Column(name="price")
    private double price;

    @ManyToMany(mappedBy = "promotionTypes", fetch = FetchType.LAZY)
    private List<Promotions> promos = new ArrayList<>();

    public Products(){}

    public void addPromotions(Promotions promotions){
        promos.add(promotions);
    }

    public List<Promotions> getPromotions(){

        return promos;
    }
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
