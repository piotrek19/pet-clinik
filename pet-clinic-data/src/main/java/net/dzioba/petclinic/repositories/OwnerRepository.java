package net.dzioba.petclinic.repositories;

import net.dzioba.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    List<Owner> findByLastNameLike(String lastName);
}
