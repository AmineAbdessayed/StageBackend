package com.task.taskbackend.Repositories;

import com.task.taskbackend.Models.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionsRepository extends JpaRepository<Promotions, Long> {
}
