package com.task.taskbackend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Promotions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Etat etat;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product_Promotion> produitPromotions;
}
