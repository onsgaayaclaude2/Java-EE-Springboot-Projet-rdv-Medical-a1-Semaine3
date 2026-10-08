package com.app.rdv.repository;

import com.app.rdv.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    // Requête dérivée : SELECT * FROM patient WHERE tel = ?
    // Optional car il se peut qu'aucun patient n'ait ce numéro
    Optional<Patient> findByTel(int tel);
    //Etape 5

    //Requetes Derivees Solutions
    //This is another solution to simplify work more
    //findByIdAndMaldie
    Optional<Patient> findByIdAndMaladiesId(Integer id, Integer maladiesId);

    // Requête dérivée à travers la relation : les patients dont une maladie porte ce nom
    List<Patient> findByMaladiesNom(String nom);



}