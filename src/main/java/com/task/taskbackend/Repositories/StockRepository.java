package com.task.taskbackend.Repositories;

import com.task.taskbackend.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {

    Stock findByName(String name);

}