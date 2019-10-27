package net.dzioba.petclinic.services.jpa;

import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.repositories.SpecialityRepository;
import net.dzioba.petclinic.repositories.VetRepository;
import net.dzioba.petclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
@Profile("jpa")
public class VetServiceJpa implements VetService {

    private final VetRepository vetRepository;
    private final SpecialityRepository specialityRepository;

    @Autowired
    public VetServiceJpa(VetRepository vetRepository, SpecialityRepository specialityRepository) {
        this.vetRepository = vetRepository;
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();
        vetRepository.findAll().forEach(vets::add);
        return vets;
    }

    @Override
    public Vet findById(Long id) {
        requireNonNull(id);
        return vetRepository.findById(id).orElse(null);
    }

    @Override
    public Vet save(Vet vet) {
        requireNonNull(vet);
        requireNonNull(vet.getLastName());
        saveUnsavedSpecialitiesOf(vet);
        return vetRepository.save(vet);
    }

    private void saveUnsavedSpecialitiesOf(Vet vet) {
        vet.getSpecialities().forEach(speciality -> {
            if (isNull(speciality.getId())){
                speciality.setId(specialityRepository.save(speciality).getId());
            }
        });
    }

    @Override
    public void deleteById(Long id) {
        requireNonNull(id);
        vetRepository.deleteById(id);
    }

    @Override
    public void delete(Vet vet) {
        requireNonNull(vet);
        vetRepository.delete(vet);
    }
}
