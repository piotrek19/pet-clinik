package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.services.PetService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceMap extends CrudServiceMap<Pet> implements PetService {
}
