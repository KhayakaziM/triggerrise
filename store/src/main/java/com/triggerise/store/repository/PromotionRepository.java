package com.triggerise.store.repository;
import com.triggerise.store.model.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PromotionRepository extends JpaRepository<Promotions,Long> {
    @Query(
            value = "SELECT * FROM  PROMOTION_TYPE  AS a  JOIN PROMOTIONS AS b ON b.ID=a.PROMOTIONS_ID",
            nativeQuery = true)
    Collection<Promotions> findPromotionIdANDProductId();

}
