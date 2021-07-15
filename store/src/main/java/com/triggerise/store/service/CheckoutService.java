package com.triggerise.store.service;

import com.triggerise.store.domain.Checkout;
import com.triggerise.store.domain.NewPromotion;
import com.triggerise.store.model.Products;
import com.triggerise.store.model.Promotions;
import com.triggerise.store.model.promotionTypes;

import java.util.Collection;
import java.util.List;

public interface CheckoutService {


    Products addProduct(Products products);
    Promotions addPromotions(NewPromotion newPromotion);
    List<Promotions> getAllPromotion();
    List<Products> getAllProduct();
    List<Products>  getProductById(String code);
    Checkout checkoutItems(Checkout checkout);
    //Collection<Promotions> getAllProductPromotion();
}
