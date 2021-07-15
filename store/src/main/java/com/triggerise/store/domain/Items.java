package com.triggerise.store.domain;

public class Items {
    private  String product;
    private int  quantity;

    public Items(){}

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
