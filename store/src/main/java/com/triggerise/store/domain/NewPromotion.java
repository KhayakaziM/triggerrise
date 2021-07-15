package com.triggerise.store.domain;

import com.triggerise.store.model.Promotions;

import java.util.List;

public class NewPromotion {
    private Promotions promotions;
    private List<Product> product;

    public NewPromotion(){}

    public Promotions getPromotions() {
        return promotions;
    }

    public void setPromotions(Promotions promotions) {
        this.promotions = promotions;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
