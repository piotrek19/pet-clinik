package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.services.PetService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PetServiceMap extends CrudServiceMap<Pet> implements PetService {

    @Override
    public Pet save(Pet pet){
        Objects.requireNonNull(pet);
        Objects.requireNonNull(pet.getPetType());
        Objects.requireNonNull(pet.getName());
        return super.save(pet);
    }
}
