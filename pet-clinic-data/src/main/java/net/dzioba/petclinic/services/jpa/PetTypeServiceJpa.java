package net.dzioba.petclinic.services.jpa;

import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.repositories.PetTypeRepository;
import net.dzioba.petclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Service
@Profile("jpa")
public class PetTypeServiceJpa implements PetTypeService {

    private final PetTypeRepository petTypeRepository;

    @Autowired
    public PetTypeServiceJpa(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<PetType> findAll() {
        Set<PetType> petTypes = new HashSet<>();
        petTypeRepository.findAll().forEach(petTypes::add);
        return petTypes;
    }

    @Override
    public PetType findById(Long id) {
        requireNonNull(id);
        return petTypeRepository.findById(id).orElse(null);
    }

    @Override
    public PetType save(PetType petType) {
        requireNonNull(petType);
        requireNonNull(petType.getName());
        return petTypeRepository.save(petType);
    }

    @Override
    public void deleteById(Long id) {
        requireNonNull(id);
        petTypeRepository.deleteById(id);
    }

    @Override
    public void delete(PetType petType) {
        requireNonNull(petType);
        petTypeRepository.delete(petType);
    }
}
