package net.dzioba.petclinic.services.jpa;

import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.repositories.PetRepository;
import net.dzioba.petclinic.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Service
@Profile("jpa")
public class PetServiceJpa implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceJpa(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {
        Set<Pet> pets = new HashSet<>();
        petRepository.findAll().forEach(pets::add);
        return pets;
    }

    @Override
    public Pet findById(Long id) {
        requireNonNull(id);
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public Pet save(Pet pet) {
        requireNonNull(pet);
        requireNonNull(pet.getPetType());
        requireNonNull(pet.getName());
        return petRepository.save(pet);
    }

    @Override
    public void deleteById(Long id) {
        requireNonNull(id);
        petRepository.deleteById(id);
    }

    @Override
    public void delete(Pet pet) {
        requireNonNull(pet);
        petRepository.delete(pet);
    }
}
