package com.triggerise.store.domain;

import com.triggerise.store.model.Products;

import java.util.List;

public class Checkout {

    private List<Product> Items;
    private double total;

    public List<Product> getItems() {
        return Items;
    }

    public void setItems(List<Product> items) {
        Items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
