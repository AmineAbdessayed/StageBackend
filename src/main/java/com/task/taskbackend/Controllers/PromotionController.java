package com.task.taskbackend.Controllers;

import com.task.taskbackend.Models.PrixCondition;
import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Promotions;
import com.task.taskbackend.Services.promotion.Dto.ProductPromotionDTO;
import com.task.taskbackend.Services.promotion.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promotions")
@CrossOrigin("*")
public class PromotionController {


    private  final PromotionService promotionService;

    @GetMapping("/listPromotions")
    public List<Promotions> getAllPromotions() {
        return promotionService.getAllPromotions();
    }

    @GetMapping("/promotions/{id}")
    public ResponseEntity<Promotions> getPromotionsById(@PathVariable Long id) {

        Promotions promotions = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotions);
    }
    @PostMapping("/addPromotion")
    public ResponseEntity<Promotions> addPromotions(@RequestBody Promotions promotions ) {

        Promotions createdProduits = promotionService.createPromotion(promotions);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduits);
    }

    @PutMapping("/updatePromotion/{id}")
    public ResponseEntity<Promotions> updatePromotion(@PathVariable Long id, @RequestBody Promotions promotions) {
        Promotions updatedProduits = promotionService.updatePromotion(id,promotions);
        return ResponseEntity.ok(updatedProduits);
    }

    @DeleteMapping("/deletePromotions/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        System.out.println("Deleting promotion with ID: " + id);
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{promotionId}/products/{productId}")
    public ResponseEntity<Void> addProductToPromotion(
            @PathVariable Long promotionId,
            @PathVariable Long productId,
            @RequestParam float discountAmount,
            @RequestParam float tauxRemise) {
        promotionService.addProductToPromotion(promotionId, productId, discountAmount, tauxRemise);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/finalPrice/{productId}")
    public ResponseEntity<Float> getFinalPrice(@PathVariable Long productId , @RequestParam PrixCondition prixCondition) {
        float finalPrice = promotionService.calculateFinalPrice(productId,prixCondition);
        return ResponseEntity.ok(finalPrice);
    }


    @GetMapping("/products/promotions")
    public ResponseEntity<List<ProductPromotionDTO>> getProductsWithPromotions() {
        List<ProductPromotionDTO> productsWithPromotions = promotionService.listProductsWithPromotions();
        return ResponseEntity.ok(productsWithPromotions);
    }

}
