/* ===== Version originale (TP1) =====

@Service
@AllArgsConstructor
public class ServicePatient implements IServicePatient {

    private PatientRepository patientRepository;

    @Override
    public Patient ajouterPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public boolean supprimerPatient(int id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }

    @Override
    public Optional<Patient> getPatientByTel(int tel) {
        return patientRepository.findByTel(tel);
    }
}

===== Fin de la version originale ===== */

package com.app.rdv.service;

import com.app.rdv.dto.PatientDTO;
import com.app.rdv.entities.Maladie;
import com.app.rdv.entities.Patient;
import com.app.rdv.mapper.PatientMapper;
import com.app.rdv.repository.MaladieRepository;
import com.app.rdv.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicePatient implements IServicePatient {

    private PatientRepository patientRepository;
    private MaladieRepository maladieRepository;
    private PatientMapper patientMapper;

    @Override
    public PatientDTO addPatient(PatientDTO dto) {
        //Etape 3
        // DTO -> Entité, on enregistre, puis Entité -> DTO
        Patient patient = patientRepository.save(patientMapper.toEntity(dto));
        return patientMapper.toDTO(patient);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        //Etape 3
        // Liste de Patient -> liste de PatientDTO
        return patientMapper.toDTOList(patientRepository.findAll());
    }

    @Override
    public boolean supprimerPatient(int id) {
        // existsById() : vérifie si un patient avec cet id existe dans la table
        if (patientRepository.existsById(id)) {
            // Le patient existe => on le supprime
            patientRepository.deleteById(id);
            return true;
        }

        else
            // Aucun patient avec cet id => rien à supprimer
            return false;
    }

    @Override
    public Optional<Patient> getPatientByTel(int tel) {
        //Application TP1
        // findByTel() : retourne une boîte Optional
        return patientRepository.findByTel(tel);
    }

    @Override
    public PatientDTO getPatientById(int id) {
        //Etape 3
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null)
            return patientMapper.toDTO(patient);

        else
            return null;
    }

    @Override
    public PatientDTO addMaladieToPatient(int patientId, int maladieId) {
        //Etape 3
        Patient patient = patientRepository.findById(patientId).orElse(null);
        Maladie maladie = maladieRepository.findById(maladieId).orElse(null);

        // Vérification 1 : le patient et la maladie existent
        if (patient == null || maladie == null)
            return null;

        // Vérification 2 (requête dérivée de Dr Amen) :
        // boîte vide => le patient ne possède pas encore cette maladie => on l'ajoute
        if (patientRepository.findByIdAndMaladiesId(patientId, maladieId).isEmpty()) {
            patient.getMaladies().add(maladie);
            patientRepository.save(patient);
        }

        return patientMapper.toDTO(patient);
    }

    @Override
    public List<PatientDTO> getPatientsByMaladie(String nom) {
        //Etape 5
        // On vérifie d'abord que la maladie existe dans la BD
        Maladie maladie = maladieRepository.findByNom(nom).orElse(null);
        if (maladie == null)
            return null;

        // Puis on récupère les patients ayant cette maladie, convertis en DTO
        return patientMapper.toDTOList(patientRepository.findByMaladiesNom(nom));
    }
}