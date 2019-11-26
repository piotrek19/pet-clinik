package net.dzioba.petclinic.api.v1.mappers;

import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PetMapperDecorator implements PetMapper {

    private PetMapper petMapper;
    private OwnerShortMapper ownerShortMapper;

    @Autowired
    @Qualifier("delegate")
    public void setPetMapper(PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    @Autowired
    public void setOwnerShortMapper(OwnerShortMapper ownerShortMapper) {
        this.ownerShortMapper = ownerShortMapper;
    }

    public PetMapper getPetMapper() {
        return petMapper;
    }

    public OwnerShortMapper getOwnerShortMapper() {
        return ownerShortMapper;
    }

    @Override
    public PetDTO petToPetDTO(Pet pet) {
        PetDTO petDTO = petMapper.petToPetDTO(pet);
        petDTO.setOwner(ownerShortMapper.ownerToOwnerShortDTO(pet.getOwner()));
        return petDTO;
    }
}
