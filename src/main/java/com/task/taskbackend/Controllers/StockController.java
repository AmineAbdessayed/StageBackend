package com.task.taskbackend.Controllers;

import com.task.taskbackend.Models.Stock;
import com.task.taskbackend.Services.StockService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@CrossOrigin("*")

public class StockController {



    private final StockService stockService;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(StockController.class);

    @GetMapping("/stocks")
    public List<Stock> getAllStock() {
        return stockService.GetAllStock();
    }

    @GetMapping("/{id}")
    public Optional<Stock> getStockById(@PathVariable Long id) {
        return stockService.getStockById(id);
    }

    @PostMapping("/addStock")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        logger.debug("POST /api/stocks/addStock called with stock: {}", stock);
        Stock createdStock = stockService.AddStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    @PutMapping("/updateStock/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        Stock updatedStock = stockService.updateStock(id, stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/deleteStock/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/StockByName")
    public ResponseEntity<Stock>GetStockByName(@RequestParam String name) {
            stockService.getStockByName(name);
            return ResponseEntity.ok().build();
    }

}
