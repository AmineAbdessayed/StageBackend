package com.task.taskbackend.Repositories;

import com.task.taskbackend.Models.Product_Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductPromotionRepository extends JpaRepository<Product_Promotion,Long> {

    @Query("SELECT pp FROM Product_Promotion pp WHERE pp.promotion.etat = 0 AND pp.product.id = :productId")
    List<Product_Promotion> findActivePromotionsForProduct(@Param("productId") Long productId);

}