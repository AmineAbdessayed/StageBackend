package com.task.taskbackend.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin("*")

public class ProduitController {

    private static final Logger logger = LoggerFactory.getLogger(ProduitController.class);


    private final ProductService produitsService;


    @GetMapping("/listProduits")
    @PreAuthorize("isAuthenticated()")

    public List<Produits> getAllProduits() {
        return produitsService.getAllProduits();
    }

    @GetMapping("/produits/{id}")
    public ResponseEntity<Produits> getProduitsById(@PathVariable Long id) {

        Produits produits = produitsService.getProduitsById(id);
        return ResponseEntity.ok(produits);
    }


    @PostMapping("/addProduits")
    public ResponseEntity<Produits> addProduits(
            @RequestPart Produits produits,
            @RequestParam("stockId") Long stockId,
            @RequestPart("file") MultipartFile file) throws IOException {
        logger.info("Stock ID: {}", stockId);

            Produits createdProduits = produitsService.createProduits(produits, stockId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduits);
    }





    @PutMapping("/updateProduit/{id}")
    public ResponseEntity<Produits> updateProduits(@PathVariable Long id, @RequestPart("produits") Produits produits, @RequestPart("file") MultipartFile file) throws IOException {
        Produits updatedProduits = produitsService.updateProduits(id, produits, file);
        return ResponseEntity.ok(updatedProduits);
    }

    @DeleteMapping("/deleteProduit/{id}")
    public ResponseEntity<Void> deleteProduits(@PathVariable Long id) {
        System.out.println("Deleting product with ID: " + id);
        produitsService.deleteProduits(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byStock")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Produits>> getProduitsByStock(@RequestParam Long stockId) {
        List<Produits> produits = produitsService.GetByStock(stockId);
        return ResponseEntity.ok(produits);
    }
}


