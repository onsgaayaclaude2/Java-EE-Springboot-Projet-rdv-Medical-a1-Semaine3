package com.app.rdv.controller;

import com.app.rdv.entities.Maladie;
import com.app.rdv.service.IServiceMaladie;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maladie")
@AllArgsConstructor
public class MaladieController {

    private IServiceMaladie iServiceMaladie;

    //Etape 1
    // Lister toutes les maladies
    @GetMapping
    public List<Maladie> findAll() {
        return iServiceMaladie.getAllMaladies();
    }

    //Etape 1
    // Ajouter une maladie (refusée si le nom existe déjà)
    @PostMapping("add")
    @ApiResponses(value = {
            // 201 : la maladie a été créée, la réponse contient un objet Maladie en JSON
            @ApiResponse(responseCode = "201", description = "Maladie Added",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Maladie.class))),

            // 409 : une maladie avec ce nom existe déjà, la réponse contient un message texte
            @ApiResponse(responseCode = "409", description = "Maladie Not Added",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string")))
    })
    public ResponseEntity<?> addMaladie(@RequestBody Maladie maladie) {

        // On appelle le service une seule fois et on garde le résultat
        Maladie maladie1 = iServiceMaladie.addMaladie(maladie);

        // maladie1 n'est pas null => la maladie a été enregistrée => 201 CREATED
        if (maladie1 != null)
            return new ResponseEntity<>(maladie1, HttpStatus.CREATED);

        else
            // maladie1 est null => nom déjà utilisé => 409 CONFLICT
            return new ResponseEntity<>("Cette maladie existe déjà", HttpStatus.CONFLICT);
    }

    //Etape 1
    // Rechercher une maladie par son nom
    @GetMapping("nom")
    @ApiResponses(value = {
            // 302 : maladie trouvée, la réponse contient un objet Maladie en JSON
            @ApiResponse(responseCode = "302", description = "Maladie Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Maladie.class))),

            // 404 : aucune maladie avec ce nom, la réponse contient un message texte
            @ApiResponse(responseCode = "404", description = "Maladie Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string")))
    })
    public ResponseEntity<?> findByNom(@RequestParam String nom) {

        // On appelle le service une seule fois et on garde le résultat
        Maladie maladie = iServiceMaladie.getMaladieByNom(nom);

        // maladie n'est pas null => trouvée => 302 FOUND
        if (maladie != null)
            return new ResponseEntity<>(maladie, HttpStatus.FOUND);

        else
            // maladie est null => aucune maladie avec ce nom => 404 NOT_FOUND
            return new ResponseEntity<>("Maladie introuvable", HttpStatus.NOT_FOUND);
    }
}