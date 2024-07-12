package com.task.taskbackend.Services.promotion;

import com.task.taskbackend.Models.*;
import com.task.taskbackend.Repositories.ProductPromotionRepository;
import com.task.taskbackend.Repositories.ProduitsRepository;
import com.task.taskbackend.Repositories.PromotionsRepository;
import com.task.taskbackend.Services.promotion.Dto.ProductPromotionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService{


    private  final PromotionsRepository promotionsRepository;
    private  final ProduitsRepository produitsRepository;
    private  final ProductPromotionRepository productPromotionRepository;

    private static final Logger logger = LoggerFactory.getLogger(PromotionServiceImpl.class);


    public List<Promotions> getAllPromotions() {
        return promotionsRepository.findAll();
    }

    public Promotions getPromotionById(Long id) {
        return promotionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotions not found with id: " + id));
    }

    public Promotions createPromotion(Promotions promotions) {

        return promotionsRepository.save(promotions);
    }

    public void deletePromotion(Long id) {
        promotionsRepository.deleteById(id);
    }

    public Promotions updatePromotion(Long id, Promotions updatedpromotions) {
        Promotions existingPromotion = promotionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produits not found with id: " + id));

        existingPromotion.setName(updatedpromotions.getName());
        existingPromotion.setDateDebut(updatedpromotions.getDateDebut());
        existingPromotion.setDateFin(updatedpromotions.getDateFin());
        existingPromotion.setEtat(updatedpromotions.getEtat());



        return promotionsRepository.save(existingPromotion);
    }

    public void addProductToPromotion(Long promotionId, Long productId, float discountAmount, float tauxRemise) {
        Optional<Produits> productOpt = produitsRepository.findById(productId);
        Optional<Promotions> promotionOpt = promotionsRepository.findById(promotionId);

        if (productOpt.isPresent() && promotionOpt.isPresent()) {
            Produits product = productOpt.get();
            Promotions promotion = promotionOpt.get();

            // Create the composite key
            ProductPromotionKey id = new ProductPromotionKey();
            id.setProductId(productId);
            id.setPromotionId(promotionId);

            // Create the Product_Promotion entity
            Product_Promotion productPromotion = new Product_Promotion();
            productPromotion.setId(id);
            productPromotion.setProduct(product);
            productPromotion.setPromotion(promotion);
            productPromotion.setDiscountAmount(discountAmount);
            productPromotion.setTauxRemise(tauxRemise);

            productPromotionRepository.save(productPromotion);
        }
    }


    public float calculateFinalPrice(Long productId , PrixCondition prixCondition) {
        Produits product = produitsRepository.findById(productId).orElse(null);
            float originalPrice = product.getPrixHc(); // or use `prixHc` depending on your pricing model

            // Fetch active promotions for the product
            List<Product_Promotion> promotions = productPromotionRepository.findActivePromotionsForProduct(productId);

            float finalPrice = originalPrice;

            for (Product_Promotion promotion : promotions) {
                if (prixCondition.equals(PrixCondition.DISCOUNT)) {
                    finalPrice -= promotion.getDiscountAmount();
                } else if (prixCondition == PrixCondition.PERCENTAGE) {
                    // Apply percentage discount
                    finalPrice -= (originalPrice * (promotion.getTauxRemise() / 100));
                }
            }

            // Ensure final price does not go below zero
            return finalPrice;


    }

    public List<ProductPromotionDTO> listProductsWithPromotions() {
        List<ProductPromotionDTO> result = new ArrayList<>();

        // Fetch all products
        List<Produits> products = produitsRepository.findAll(); // Assuming you still need all products to check for promotions

        for (Produits product : products) {
            // Fetch active promotions for the current product
            List<Product_Promotion> promotions = productPromotionRepository.findActivePromotionsForProduct(product.getId());
            float finalPrice = product.getPrixHt(); // or use `prixHc`
            List<String> promotionNames = new ArrayList<>();

            if (!promotions.isEmpty()) {
                for (Product_Promotion promotion : promotions) {
                    if (promotion.getTauxRemise() > 0) {
                        finalPrice -= (finalPrice * (promotion.getTauxRemise() / 100));
                    } else {
                        finalPrice -= promotion.getDiscountAmount();
                    }
                    promotionNames.add(promotion.getPromotion().getName()); // Add promotion name
                }

                result.add(new ProductPromotionDTO(product.getId(), product.getLibelle(), product.getPrixHt(), finalPrice, promotionNames));
            }
        }

        return result;
    }




}
