package com.task.taskbackend.Services;

import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Stock;
import com.task.taskbackend.Repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StockService {


    private  final StockRepository stockRepository;


    public Stock AddStock(Stock stock){
             stockRepository.save(stock);
        return stock;
    }
    public List<Stock> GetAllStock() {
        return stockRepository.findAll().stream()
                .peek(stock -> Hibernate.initialize(stock.getProduits()))
                .collect(Collectors.toList());
    }

    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    public Stock updateStock(Long id, Stock updatedStock) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));

        stock.setName(updatedStock.getName());

        return stockRepository.save(stock);
    }


    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
    public Stock getStockByName(String name) {

        return stockRepository.findByName(name);
    }
    public List<Produits> getProduitsByStockId(Long stockID) {
        Stock stock = stockRepository.findById(stockID).orElse(null);
        if (stock != null) {
            return new ArrayList<>(stock.getProduits());
        }
        return Collections.emptyList();
    }


}
