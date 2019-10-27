package net.dzioba.petclinic.services.jpa;

import net.dzioba.petclinic.model.Visit;
import net.dzioba.petclinic.repositories.VisitRepository;
import net.dzioba.petclinic.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Service
@Profile("jpa")
public class VisitServiceJpa implements VisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitServiceJpa(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        visitRepository.findAll().forEach(visits::add);
        return visits;
    }

    @Override
    public Visit findById(Long id) {
        requireNonNull(id);
        return visitRepository.findById(id).orElse(null);
    }

    @Override
    public Visit save(Visit visit) {
        requireNonNull(visit);
        requireNonNull(visit.getDate());
        requireNonNull(visit.getPet());
        requireNonNull(visit.getPet().getId());
        requireNonNull(visit.getPet().getOwner());
        requireNonNull(visit.getPet().getOwner().getId());

        return visitRepository.save(visit);
    }

    @Override
    public void deleteById(Long id) {
        requireNonNull(id);
        visitRepository.deleteById(id);
    }

    @Override
    public void delete(Visit visit) {
        requireNonNull(visit);
        visitRepository.delete(visit);
    }
}
