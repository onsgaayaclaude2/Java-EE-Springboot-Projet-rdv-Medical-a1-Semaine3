package com.app.rdv.mapper;

import com.app.rdv.dto.PatientDTO;
import com.app.rdv.entities.Maladie;
import com.app.rdv.entities.Patient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientMapper {

    //Etape 2
    // DTO -> Entité (la liste des maladies est laissée vide)
    public Patient toEntity(PatientDTO dto) {
        return new Patient(dto.getId(), dto.getNom(), dto.getPrenom(), dto.getAge(), dto.getTel(), List.of());
    }

    //Etape 2
    // Entité -> DTO (on garde seulement le nom des maladies)
    public PatientDTO toDTO(Patient patient) {
        return new PatientDTO(patient.getId(), patient.getNom(), patient.getPrenom(), patient.getAge(), patient.getTel(),
                patient.getMaladies().stream().map(Maladie::getNom).toList());
    }

    //Etape 2
    // Liste de Patient -> liste de PatientDTO (toDTO sur chaque patient)
    public List<PatientDTO> toDTOList(List<Patient> patients) {
        return patients.stream().map(this::toDTO).toList();
    }
}