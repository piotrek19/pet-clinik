package net.dzioba.petclinic.services.jpa;

import net.dzioba.petclinic.model.Speciality;
import net.dzioba.petclinic.repositories.SpecialityRepository;
import net.dzioba.petclinic.services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Service
@Profile("jpa")
public class SpecialityServiceJpa implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    @Autowired
    public SpecialityServiceJpa(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> specialities = new HashSet<>();
        specialityRepository.findAll().forEach(specialities::add);
        return specialities;
    }

    @Override
    public Speciality findById(Long id) {
        requireNonNull(id);
        return specialityRepository.findById(id).orElse(null);
    }

    @Override
    public Speciality save(Speciality speciality) {
        requireNonNull(speciality);
        requireNonNull(speciality.getDescription());
        return specialityRepository.save(speciality);
    }

    @Override
    public void deleteById(Long id) {
        requireNonNull(id);
        specialityRepository.deleteById(id);
    }

    @Override
    public void delete(Speciality speciality) {
        requireNonNull(speciality);
        specialityRepository.delete(speciality);
    }
}
