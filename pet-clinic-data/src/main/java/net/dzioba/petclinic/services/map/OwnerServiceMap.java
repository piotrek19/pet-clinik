package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.services.OwnerService;
import net.dzioba.petclinic.services.PetService;
import net.dzioba.petclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends CrudServiceMap<Owner> implements OwnerService {

    private final PetService petService;
    private final PetTypeService petTypeService;

    @Autowired
    public OwnerServiceMap(PetService petService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public List<Owner> findByLastNameLike(String lastName) {
        // TODO should be changed to compare using wildcards before and after lastName: *lastName*
        requireNonNull(lastName);

        Collection<Owner> values = map.values();
        List<Owner> resultCollection = new ArrayList<>();
        for (Owner value : values) {
            if (lastName.equals(value.getLastName()))
                resultCollection.add(value);   // Owner found.
        }

        return resultCollection;
    }

    @Override
    public Owner save(Owner owner){
        requireNonNull(owner);
        requireNonNull(owner.getLastName());
        requireNonNull(owner.getAddress());
        saveUnsavedPetsOf(owner);
        return super.save(owner);
    }

    private void saveUnsavedPetsOf(Owner owner) {
        owner.getPets().forEach(pet -> {
            if (isNull(pet.getId())){
                saveUnsavedPetType(pet.getPetType());
                petService.save(pet);
            }
        });
    }

    private void saveUnsavedPetType(PetType petType) {
        requireNonNull(petType);
        if (isNull(petType.getId())){
            petTypeService.save(petType);
        }
    }


}
