package com.triggerise.store.controller;

import com.triggerise.store.domain.Checkout;
import com.triggerise.store.domain.NewPromotion;
import com.triggerise.store.model.Products;
import com.triggerise.store.model.Promotions;
import com.triggerise.store.model.promotionTypes;
import com.triggerise.store.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class StoreController {
    @Autowired
    private CheckoutService checkoutService;
    @PostMapping("/store/add-product")
    public ResponseEntity<Products> addProduct(@RequestBody Products products ){
        return ResponseEntity.ok().body(this.checkoutService.addProduct(products));
    }
    @PostMapping("/store/add-promotion")
    public ResponseEntity<Promotions> addPromotions(@RequestBody NewPromotion newPromotion){
        return ResponseEntity.ok().body(this.checkoutService.addPromotions(newPromotion));
    }
    @GetMapping("/store/products")
    public ResponseEntity<List<Products>> getAllProduct(){
        return ResponseEntity.ok().body(checkoutService.getAllProduct());
    }
    @GetMapping("/store/products/{product-id}")
    public ResponseEntity<List<Products>> getProductById(@PathVariable ("product-id")  String code){
        return ResponseEntity.ok().body(checkoutService.getProductById(code));
    }
   @GetMapping("/store/promotions")
    public ResponseEntity<Collection<Promotions>> getAllPromotion(){
        return ResponseEntity.ok().body(checkoutService.getAllPromotion());
    }

    @PostMapping("/store/checkout-items")
    public ResponseEntity<Checkout> checkoutItems(@RequestBody Checkout checkout){
        return ResponseEntity.ok().body(this.checkoutService.checkoutItems(checkout));
    }
}
