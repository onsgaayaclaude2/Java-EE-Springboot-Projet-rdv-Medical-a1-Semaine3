package com.app.rdv.service;

import com.app.rdv.entities.Maladie;
import com.app.rdv.repository.MaladieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServiceMaladie implements IServiceMaladie {

    private MaladieRepository maladieRepository;

    @Override
    public Maladie addMaladie(Maladie maladie) {
        Optional<Maladie> maladie1 = maladieRepository.findByNom(maladie.getNom());
        if (maladie1.isEmpty())
            return maladieRepository.save(maladie);
        return null;
    }

    @Override
    public Maladie getMaladieByNom(String nom) {
        return maladieRepository.findByNom(nom).orElse(null);
    }

    @Override
    public List<Maladie> getAllMaladies() {
        return maladieRepository.findAll();
    }

    @Override
    public Maladie getMaladieById(int id) {
        return maladieRepository.findById(id).orElse(null);
    }
}