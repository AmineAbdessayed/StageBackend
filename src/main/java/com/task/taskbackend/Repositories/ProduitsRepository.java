package com.task.taskbackend.Repositories;

import com.task.taskbackend.Models.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProduitsRepository extends JpaRepository<Produits,Long> {

    List<Produits> findByStockId(Long StockId);

}
