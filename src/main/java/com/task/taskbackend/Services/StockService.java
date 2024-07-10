package com.task.taskbackend.Services;

import com.task.taskbackend.Models.Stock;
import com.task.taskbackend.Repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StockService {


    private  final StockRepository stockRepository;


    public Stock AddStock(Stock stock){
             stockRepository.save(stock);
        return stock;
    }

    public List<Stock> GetAllStock(){

       return stockRepository.findAll();
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



}
