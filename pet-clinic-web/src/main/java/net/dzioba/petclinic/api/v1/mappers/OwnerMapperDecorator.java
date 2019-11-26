package net.dzioba.petclinic.api.v1.mappers;

import net.dzioba.petclinic.api.v1.model.OwnerDTO;
import net.dzioba.petclinic.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.stream.Collectors;


public class OwnerMapperDecorator implements OwnerMapper {

    private OwnerMapper ownerMapper;
    private PetShortMapper petShortMapper;

    @Autowired
    public void setOwnerMapper(@Qualifier("delegate") OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

    @Autowired
    public void setPetShortMapper(PetShortMapper petShortMapper) {
        this.petShortMapper = petShortMapper;
    }

    public OwnerMapper getOwnerMapper() {
        return ownerMapper;
    }

    public PetShortMapper getPetShortMapper() {
        return petShortMapper;
    }

    @Override
    public OwnerDTO ownerToOwnerDTO(Owner owner) {
        OwnerDTO ownerDTO = ownerMapper.ownerToOwnerDTO(owner);
        ownerDTO.setPets(owner.getPets().stream()
                        .map(petShortMapper::petToPetShortDTO)
                        .collect(Collectors.toSet())
        );
        return ownerDTO;
    }
}
