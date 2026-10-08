package com.app.rdv.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class PatientDTO {

    private int id;
    private String nom;
    private String prenom;
    private int age;
    private int tel;
    private List<String> maladies;

}
