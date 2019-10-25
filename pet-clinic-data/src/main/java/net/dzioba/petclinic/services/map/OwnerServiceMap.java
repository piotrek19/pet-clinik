package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.services.OwnerService;
import net.dzioba.petclinic.services.PetService;
import net.dzioba.petclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class OwnerServiceMap extends CrudServiceMap<Owner> implements OwnerService {

    private PetService petService;
    private PetTypeService petTypeService;

    @Autowired
    public OwnerServiceMap(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String lastName) {
        Objects.requireNonNull(lastName);

        Collection<Owner> values = map.values();
        for (Owner value : values) {
            if (lastName.equals(value.getLastName()))
                return value;   // Owner found.
        }
        // Owner not found:
        return null;
    }

    @Override
    public Owner save(Owner owner){
        Objects.requireNonNull(owner);
        Objects.requireNonNull(owner.getLastName());
        Objects.requireNonNull(owner.getAddress());
        saveUnsavedPetsOf(owner);
        return super.save(owner);
    }

    private void saveUnsavedPetsOf(Owner owner) {
        owner.getPets().forEach(pet -> {
            if (Objects.isNull(pet.getId())){
                saveUnsavedPetType(pet.getPetType());
                petService.save(pet);
            }
        });
    }

    private void saveUnsavedPetType(PetType petType) {
        if (Objects.isNull(petType.getId())){
            petTypeService.save(petType);
        }
    }


}
