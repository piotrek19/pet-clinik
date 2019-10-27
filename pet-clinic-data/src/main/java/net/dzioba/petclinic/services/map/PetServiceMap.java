package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
@Profile({"default", "map"})
public class PetServiceMap extends CrudServiceMap<Pet> implements PetService {

    @Override
    public Pet save(Pet pet){
        requireNonNull(pet);
        requireNonNull(pet.getPetType());
        requireNonNull(pet.getName());
        return super.save(pet);
    }
}
