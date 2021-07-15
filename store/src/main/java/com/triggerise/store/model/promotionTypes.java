package com.triggerise.store.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;



@Table(name="Promotion_Type")
public class promotionTypes  {

    @Id
    private Long id;
    @Column(name="Promotions_id")
    private Long PromotionsId;

    @Column(name="Products_id")
    private Long ProductsId;

    public  promotionTypes(){}

    public Long getPromotionsId() {
        return PromotionsId;
    }

    public void setPromotionsId(Long promotionsId) {
        PromotionsId = promotionsId;
    }

    public Long getProductsId() {
        return ProductsId;
    }

    public void setProductsId(Long productsId) {
        ProductsId = productsId;
    }

}
