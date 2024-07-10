package com.task.taskbackend.Services;

import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Stock;
import com.task.taskbackend.Repositories.ProduitsRepository;
import com.task.taskbackend.Repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Produits createProduits(Produits produits, long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + stockId));
        produits.setStock(stock);
        return produitsRepository.save(produits);
    }

    public Produits updateProduits(Long id, Produits updatedProduits) {
        Produits existingProduits = produitsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produits not found with id: " + id));

        existingProduits.setLibelle(updatedProduits.getLibelle());
        existingProduits.setDescription(updatedProduits.getDescription());
        existingProduits.setPrixHt(updatedProduits.getPrixHt());
        existingProduits.setPrixHc(updatedProduits.getPrixHc());
        existingProduits.setTauxTva(updatedProduits.getTauxTva());
        existingProduits.setStock(updatedProduits.getStock());


        return produitsRepository.save(existingProduits);
    }

    public void deleteProduits(Long id) {
        produitsRepository.deleteById(id);
    }

    public List<Produits> GetByStock(Long StockId) {
        return produitsRepository.findByStockId(StockId);
    }
}
