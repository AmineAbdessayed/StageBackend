package com.task.taskbackend.Services;

import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Stock;
import com.task.taskbackend.Repositories.ProduitsRepository;
import com.task.taskbackend.Repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProduitsRepository produitsRepository;
    private final StockRepository stockRepository;


    public List<Produits> getAllProduits() {
        return produitsRepository.findAll();
    }

    public Produits getProduitsById(Long id) {
        return produitsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produits not found with id: " + id));
    }

    public Produits createProduits(Produits produits, long stockId, MultipartFile file) throws IOException {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + stockId));
        produits.setStock(stock);
        produits.setDisplayPicture(file.getBytes());
        Produits savedProduits = produitsRepository.save(produits);
        savedProduits.setDisplayPicture(null); // Hide the picture in the response if necessary
        return savedProduits;
    }

    public Produits updateProduits(Long id, Produits updatedProduits, MultipartFile file) throws IOException {
        Produits existingProduits = produitsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produits not found with id: " + id));

        existingProduits.setLibelle(updatedProduits.getLibelle());
        existingProduits.setDescription(updatedProduits.getDescription());
        existingProduits.setPrixHt(updatedProduits.getPrixHt());
        existingProduits.setPrixHc(updatedProduits.getPrixHc());
        existingProduits.setTauxTva(updatedProduits.getTauxTva());

        if (updatedProduits.getStock() != null) {
            Stock stock = stockRepository.findById(updatedProduits.getStock().getId())
                    .orElseThrow(() -> new RuntimeException("Stock not found with id: " + updatedProduits.getStock().getId()));
            existingProduits.setStock(stock);
        }

        if (file != null && !file.isEmpty()) {
            String pictureUrl = saveFile(file);
        }

        return produitsRepository.save(existingProduits);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String uploadDir = "uploads/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        File destinationFile = new File(uploadDir + fileName);
        file.transferTo(destinationFile);
        return destinationFile.getAbsolutePath();
    }


    public void deleteProduits(Long id) {
        produitsRepository.deleteById(id);
    }

    public List<Produits> GetByStock(Long StockId) {
        return produitsRepository.findByStockId(StockId);
    }
}
