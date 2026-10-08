package com.app.rdv.service;

import com.app.rdv.entities.Maladie;

import java.util.List;

public interface IServiceMaladie {

    // Ajouter une maladie
    Maladie addMaladie(Maladie maladie);

    // Lister toutes les maladies
    List<Maladie> getAllMaladies();

    // Chercher une maladie par son nom
    Maladie getMaladieByNom(String nom);

    // Chercher une maladie par son id
    Maladie getMaladieById(int id);
}