package net.dzioba.petclinic.services;

import net.dzioba.petclinic.model.Vet;

import java.util.Set;

public interface VetService {

    Vet findById(Long id);

    Set<Vet> findAll();

    Vet save(Vet vet);
}
