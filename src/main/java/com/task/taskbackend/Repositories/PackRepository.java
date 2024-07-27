package com.task.taskbackend.Repositories;

import com.task.taskbackend.Models.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackRepository extends JpaRepository<Pack, Long> {

    @Query("SELECT p FROM Pack p LEFT JOIN FETCH p.produits")
    List<Pack> findAllWithProducts();
}
