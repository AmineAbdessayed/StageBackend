package com.task.taskbackend.Controllers;

import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Promotions;
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






}
