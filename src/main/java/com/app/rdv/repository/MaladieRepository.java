package com.app.rdv.repository;

import com.app.rdv.entities.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaladieRepository extends JpaRepository<Maladie, Integer> {

    // Requête dérivée : SELECT * FROM maladie WHERE nom = ?
    // Retourne la maladie, ou null si aucune maladie n'a ce nom
    Optional<Maladie> findByNom(String nom);
}