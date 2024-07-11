package com.task.taskbackend.Services.promotion;

import com.task.taskbackend.Models.Etat;
import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Promotions;
import com.task.taskbackend.Repositories.PromotionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService{


    private  final PromotionsRepository promotionsRepository;


    public List<Promotions> getAllPromotions() {
        return promotionsRepository.findAll();
    }

    public Promotions getPromotionById(Long id) {
        return promotionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotions not found with id: " + id));
    }

    public Promotions createPromotion(Promotions promotions) {

        return promotionsRepository.save(promotions);
    }

    public void deletePromotion(Long id) {
        promotionsRepository.deleteById(id);
    }

    public Promotions updatePromotion(Long id, Promotions updatedpromotions) {
        Promotions existingPromotion = promotionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produits not found with id: " + id));

        existingPromotion.setName(updatedpromotions.getName());
        existingPromotion.setDateDebut(updatedpromotions.getDateDebut());
        existingPromotion.setDateFin(updatedpromotions.getDateFin());
        existingPromotion.setEtat(updatedpromotions.getEtat());



        return promotionsRepository.save(existingPromotion);
    }



}
