package com.task.taskbackend.Services.promotion;

import com.task.taskbackend.Models.PrixCondition;
import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Promotions;
import com.task.taskbackend.Services.promotion.Dto.ProductPromotionDTO;

import java.util.List;

public interface PromotionService {

     List<Promotions> getAllPromotions();
     Promotions getPromotionById(Long id);
     Promotions createPromotion(Promotions promotions) ;
     void deletePromotion(Long id) ;
     Promotions updatePromotion(Long id, Promotions updatedpromotions) ;
     public void addProductToPromotion(Long promotionId, Long productId, float discountAmount, float tauxRemise) ;
     public float calculateFinalPrice(Long productId , PrixCondition prixCondition) ;
     public List<ProductPromotionDTO> listProductsWithPromotions() ;






     }
