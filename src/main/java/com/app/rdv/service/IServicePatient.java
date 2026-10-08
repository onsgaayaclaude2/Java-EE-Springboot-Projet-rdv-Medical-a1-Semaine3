package com.app.rdv.service;

import com.app.rdv.dto.PatientDTO;
import com.app.rdv.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface IServicePatient {

    //Etape 3
    PatientDTO addPatient(PatientDTO dto);

    //Etape 3
    List<PatientDTO> getAllPatients();

    // TP1 : supprimer un patient par son id
    boolean supprimerPatient(int id);

    // TP1 Application : chercher un patient par son téléphone
    Optional<Patient> getPatientByTel(int tel);

    //Etape 3
    PatientDTO getPatientById(int id);


    //Etape 3
    PatientDTO addMaladieToPatient(int patientId, int maladieId);

    //Etape 5
    List<PatientDTO> getPatientsByMaladie(String nom);
}

/* ===== Version originale (TP1) =====

package com.app.rdv.service;

import com.app.rdv.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface IServicePatient {

    // Ajouter un patient
    Patient ajouterPatient(Patient patient);

    // Lister tous les patients
    List<Patient> getAllPatients();

    // Supprimer un patient par son id (true si supprimé, false s'il n'existe pas)
    boolean supprimerPatient(int id);

    //Application
    // Chercher un patient par son numéro de téléphone
    Optional<Patient> getPatientByTel(int tel);

    Optional<Patient> getPatientById(string nom);
}

===== Fin de la version originale ===== */