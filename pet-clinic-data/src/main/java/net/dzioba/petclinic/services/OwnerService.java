package net.dzioba.petclinic.services;

import net.dzioba.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner> {

    Owner findByLastName(String lastName);

}
