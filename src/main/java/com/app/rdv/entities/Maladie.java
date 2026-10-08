package com.app.rdv.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity

public class Maladie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    // Contrainte : le nom de la maladie est unique et non nul
    @Column(unique = true, nullable = false)
    private String nom;

    private String description;


}
