package net.dzioba.petclinic.services;

import net.dzioba.petclinic.model.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner> {

    List<Owner> findByLastNameLike(String lastName);

}
