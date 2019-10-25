package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PetTypeServiceMap extends CrudServiceMap<PetType> implements PetTypeService {

    @Override
    public PetType save(PetType petType){
        Objects.requireNonNull(petType);
        Objects.requireNonNull(petType.getName());
        return super.save(petType);
    }
}
