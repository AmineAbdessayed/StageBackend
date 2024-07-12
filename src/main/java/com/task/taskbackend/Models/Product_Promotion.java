package com.task.taskbackend.Models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "product_promotions")
@Getter
@Setter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Product_Promotion {

    @EmbeddedId
    private ProductPromotionKey id;


    @ManyToOne
    @MapsId("productId")
    private Produits product;

    @ManyToOne
    @MapsId("promotionId")
    private Promotions promotion;

    private float discountAmount;
    private float tauxRemise;


}
