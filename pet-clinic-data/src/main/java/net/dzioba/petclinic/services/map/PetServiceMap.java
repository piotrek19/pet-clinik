package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.services.PetService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PetServiceMap extends CrudServiceMap<Pet, Long> implements PetService {

    @Override
    public Pet save(Pet object) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(object.getId());

        return save(object.getId(), object);
    }
}
