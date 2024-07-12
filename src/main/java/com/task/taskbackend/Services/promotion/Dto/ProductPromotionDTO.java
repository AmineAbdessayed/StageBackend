package com.task.taskbackend.Services.promotion.Dto;

import com.task.taskbackend.Models.Produits;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ProductPromotionDTO {
    private Long productId;
    private String productName;
    private float originalPrice;
    private float finalPrice;
    private List<String> promotionNames;

    public ProductPromotionDTO(Long productId, String productName, float originalPrice, float finalPrice, List<String> promotionNames) {
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.promotionNames = promotionNames;
    }
}
