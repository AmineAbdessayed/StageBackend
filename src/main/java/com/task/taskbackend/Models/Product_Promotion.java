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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Produits product;

    @Id
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotions promotion;

    private float discountAmount;
    private float tauxRemise;


}
