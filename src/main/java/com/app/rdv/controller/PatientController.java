package com.app.rdv.controller;

import com.app.rdv.dto.PatientDTO;
import com.app.rdv.entities.Patient;
import com.app.rdv.service.IServicePatient;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
@AllArgsConstructor
public class PatientController {

    private IServicePatient iServicePatient;

    //Etape 4
    @GetMapping
    public List<PatientDTO> findAll() {
        return iServicePatient.getAllPatients();
    }

    //Etape 4
    @PostMapping("add")
    public PatientDTO addPatient(@RequestBody PatientDTO dto) {

        return iServicePatient.addPatient(dto);
    }
    //Delete is a plus not in The TP Subject (Enoncé )
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        if (iServicePatient.supprimerPatient(id)) {
            return new ResponseEntity<>("Patient supprimé", HttpStatus.OK);
        }
        return new ResponseEntity<>("Patient introuvable", HttpStatus.NOT_FOUND);
    }

    // Recherche d'un patient par son numéro de téléphone
    @GetMapping("tel")
    @ApiResponses(value = {
            // 302 : patient trouvé, la réponse contient un objet Patient en JSON
            @ApiResponse(responseCode = "302", description = "Patient Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class))),

            // 404 : aucun patient avec ce numéro, la réponse contient un message texte
            @ApiResponse(responseCode = "404", description = "Patient Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string")))
    })
    public ResponseEntity<?> findByTel(@RequestParam int tel) {

        // On appelle le service une seule fois : on reçoit une boîte Optional
        Optional<Patient> patient = iServicePatient.getPatientByTel(tel);

        // isPresent() : la boîte contient un patient => trouvé => 302 FOUND
        if (patient.isPresent()) {
            // get() : on sort le patient de la boîte pour le mettre dans la réponse
            return new ResponseEntity<>(patient.get(), HttpStatus.FOUND);
        }

        // La boîte est vide => aucun patient avec ce numéro => 404 NOT_FOUND
        return new ResponseEntity<>("Patient introuvable", HttpStatus.NOT_FOUND);
    }

    //Etape 1 et 4
    // Recherche d'un patient par son id
    @GetMapping("id/{id}")
    @ApiResponses(value = {
            // 302 : patient trouvé, la réponse contient un PatientDTO en JSON
            @ApiResponse(responseCode = "302", description = "Patient Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))),

            // 404 : aucun patient avec cet id, la réponse contient un message texte
            @ApiResponse(responseCode = "404", description = "Patient Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string")))
    })
    public ResponseEntity<?> findById(@PathVariable int id) {

        // On appelle le service une seule fois et on garde le résultat
        PatientDTO dto = iServicePatient.getPatientById(id);

        // dto n'est pas null => patient trouvé => 302 FOUND
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.FOUND);
        }

        // dto est null => aucun patient avec cet id => 404 NOT_FOUND
        return new ResponseEntity<>("Patient introuvable", HttpStatus.NOT_FOUND);
    }

    //Etape 4
    // Ajout d'une maladie (par son id) à un patient (par son id)
    @PostMapping("{patientId}/maladie/{maladieId}")
    @ApiResponses(value = {
            // 200 : la réponse contient le PatientDTO avec sa liste de maladies
            @ApiResponse(responseCode = "200", description = "Maladie Added To Patient",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))),

            // 404 : le patient ou la maladie n'existe pas
            @ApiResponse(responseCode = "404", description = "Patient Or Maladie Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string")))
    })
    public ResponseEntity<?> addMaladieToPatient(@PathVariable int patientId, @PathVariable int maladieId) {

        // On appelle le service une seule fois et on garde le résultat
        PatientDTO dto = iServicePatient.addMaladieToPatient(patientId, maladieId);

        // dto n'est pas null => le patient et la maladie existent => 200 OK
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

        // dto est null => patient ou maladie introuvable => 404 NOT_FOUND
        return new ResponseEntity<>("Patient ou maladie introuvable", HttpStatus.NOT_FOUND);
    }

    //Etape 5
    // Liste des patients ayant une maladie (identifiée par son nom)
    @GetMapping("maladie")
    @ApiResponses(value = {
            // 200 : la maladie existe, la réponse contient la liste des PatientDTO
            @ApiResponse(responseCode = "200", description = "Patients Found",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatientDTO.class)))),

            // 404 : aucune maladie avec ce nom
            @ApiResponse(responseCode = "404", description = "Maladie Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string")))
    })
    public ResponseEntity<?> findByMaladie(@RequestParam String nom) {

        // On appelle le service une seule fois et on garde le résultat
        List<PatientDTO> dtos = iServicePatient.getPatientsByMaladie(nom);

        // dtos n'est pas null => la maladie existe => 200 OK avec la liste
        if (dtos != null) {
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }

        // dtos est null => la maladie n'existe pas => 404 NOT_FOUND
        return new ResponseEntity<>("Maladie introuvable", HttpStatus.NOT_FOUND);
    }
}


/*package com.app.rdv.controller;

import com.app.rdv.entities.Patient;
import com.app.rdv.service.IServicePatient;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
@AllArgsConstructor
public class PatientController {

    private IServicePatient iServicePatient;

    @GetMapping
    public List<Patient> findAll() {
        return iServicePatient.getAllPatients();
    }

    @PostMapping("add")
    public Patient addPatient(@RequestBody Patient patient) {

        return iServicePatient.ajouterPatient(patient);
    }
//Delete is a plus not in The TP Subject (Enoncé )
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        if (iServicePatient.supprimerPatient(id)) {
            return new ResponseEntity<>("Patient supprimé", HttpStatus.OK);
        }
        return new ResponseEntity<>("Patient introuvable", HttpStatus.NOT_FOUND);
    }

    // Recherche d'un patient par son numéro de téléphone
    @GetMapping("tel")
    @ApiResponses(value = {
            // 302 : patient trouvé, la réponse contient un objet Patient en JSON
            @ApiResponse(responseCode = "302", description = "Patient Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class))),

            // 404 : aucun patient avec ce numéro, la réponse contient un message texte
            @ApiResponse(responseCode = "404", description = "Patient Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string")))
    })
    public ResponseEntity<?> findByTel(@RequestParam int tel) {

        // On appelle le service une seule fois : on reçoit une boîte Optional
        Optional<Patient> patient = iServicePatient.getPatientByTel(tel);

        // isPresent() : la boîte contient un patient => trouvé => 302 FOUND
        if (patient.isPresent()) {
            // get() : on sort le patient de la boîte pour le mettre dans la réponse
            return new ResponseEntity<>(patient.get(), HttpStatus.FOUND);
        }

        // La boîte est vide => aucun patient avec ce numéro => 404 NOT_FOUND
        return new ResponseEntity<>("Patient introuvable", HttpStatus.NOT_FOUND);
    }
}




 */