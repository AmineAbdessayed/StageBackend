package com.task.taskbackend.Services.promotion;

import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Promotions;

import java.util.List;

public interface PromotionService {

     List<Promotions> getAllPromotions();
     Promotions getPromotionById(Long id);
     Promotions createPromotion(Promotions promotions) ;
     void deletePromotion(Long id) ;
     Promotions updatePromotion(Long id, Promotions updatedpromotions) ;




    }
