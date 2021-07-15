package com.triggerise.store.repository;

import com.triggerise.store.model.Promotions;
import com.triggerise.store.model.promotionTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProductPromotion  extends JpaRepository<Promotions,Long> {

    @Query(
            value = "SELECT * FROM  PROMOTION_TYPE  AS a  JOIN PROMOTIONS AS b ON b.ID=a.PROMOTIONS_ID",
            nativeQuery = true)
    Collection<Promotions> findPromotionIdANDProductId();

}
