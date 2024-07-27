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
@Builder
public class Pack {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Etat etat;
    @Lob
    @Column(length = 1000000)
    private byte[] displayPicture;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Produits> produits;
}
