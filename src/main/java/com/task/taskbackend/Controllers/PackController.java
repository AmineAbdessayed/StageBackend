package com.task.taskbackend.Controllers;


import com.task.taskbackend.Models.Pack;
import com.task.taskbackend.Models.PrixCondition;
import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Services.PackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pack")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PackController {


    private  final PackService packService;



    @GetMapping("/listPacks")

    public List<Pack> getAllPacks() {
        return packService.getAllPacks();
    }

    @GetMapping("/packs/{id}")
    public ResponseEntity<Pack> getPackById(@PathVariable Long id) {

        Pack packs = packService.getPackById(id);
        return ResponseEntity.ok(packs);
    }

    @GetMapping("/AffectProduct/{idPack}/{idProduit}")
    public ResponseEntity<Pack> AffectProductToPack(@PathVariable Long idPack , @PathVariable Long idProduit) throws IOException {

        Pack packs = packService.AddProductToPack(idPack,idProduit);
        return ResponseEntity.ok(packs);
    }


    @PostMapping("/addPacks")
    public ResponseEntity<Pack> addPack(
            @RequestPart Pack packs,
            @RequestPart("file") MultipartFile file) throws IOException {

        Pack createdPack = packService.addPack(packs, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPack);
    }
    @DeleteMapping("/deletePack/{id}")
    public ResponseEntity<Void> deletePack(@PathVariable Long id) {
        System.out.println("Deleting pack with ID: " + id);
        packService.deletePack(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{packId}/apply-discount")
    public ResponseEntity<List<Produits>> applyDiscountToPack(
            @PathVariable Long packId,
            @RequestParam double discountValue,
            @RequestParam PrixCondition prixCondition) {

        List<Produits> updatedProducts = packService.RemiseProduits(packId, discountValue, prixCondition);
        return ResponseEntity.ok(updatedProducts);
    }

}
