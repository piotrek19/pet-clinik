package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
@Profile({"default", "map"})
public class PetTypeServiceMap extends CrudServiceMap<PetType> implements PetTypeService {

    @Override
    public PetType save(PetType petType){
        requireNonNull(petType);
        requireNonNull(petType.getName());
        return super.save(petType);
    }
}
