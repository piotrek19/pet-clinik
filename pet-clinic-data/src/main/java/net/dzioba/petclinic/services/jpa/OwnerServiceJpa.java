package net.dzioba.petclinic.services.jpa;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.repositories.OwnerRepository;
import net.dzioba.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Service
@Profile("jpa")
public class OwnerServiceJpa implements OwnerService {

    final private OwnerRepository ownerRepository;

    @Autowired
    public OwnerServiceJpa(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> findByLastNameLike(String lastName) {
        requireNonNull(lastName);
        return ownerRepository.findByLastNameLike(lastName);
    }

    @Override
    public Set<Owner> findAll() {
        Set<Owner> owners = new HashSet<>();
        ownerRepository.findAll().forEach(owners::add);
        return owners;
    }

    @Override
    public Owner findById(Long id) {
        requireNonNull(id);
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public Owner save(Owner owner) {
        requireNonNull(owner);
        requireNonNull(owner.getLastName());
        requireNonNull(owner.getAddress());
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteById(Long id) {
        requireNonNull(id);
        ownerRepository.deleteById(id);
    }

    @Override
    public void delete(Owner owner) {
        requireNonNull(owner);
        ownerRepository.delete(owner);
    }
}
