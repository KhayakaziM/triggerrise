package com.triggerise.store.repository;

import com.triggerise.store.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {

    List<Products>findByCode(String code);



}
