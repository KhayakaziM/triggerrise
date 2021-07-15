package com.triggerise.store.model;



import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="Promotions")
public class Promotions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="code")
    private String code;
    @Column(name="Promo_Start_DT")
    private Date start_date;
    @Column(name="Promo_end_DT")
    private Date end_date;
    @Column(name="disc_amount")
    private double discount_amount;
    @Column(name="disc_percent")
    private int discount_percentage;
    @Column(name="condition")
    private String description;
    @Column(name="quantity")
    private int quantity;

    @ManyToMany
    @JoinTable(name = "Promotion_Type",
            joinColumns = { @JoinColumn(name = "Promotions_id", nullable = false, updatable = false)},
            inverseJoinColumns = { @JoinColumn(name = "Products_id", nullable = false, updatable = false)})
    private List<Products> promotionTypes = new ArrayList<>();

    public Promotions(){}

    public void promotiontype(Products products){
        promotionTypes.add(products);

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public int getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(int discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
