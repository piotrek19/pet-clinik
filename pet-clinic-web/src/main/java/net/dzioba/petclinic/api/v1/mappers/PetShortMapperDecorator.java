package net.dzioba.petclinic.api.v1.mappers;

import net.dzioba.petclinic.api.v1.controllers.OwnerDTOController;
import net.dzioba.petclinic.api.v1.model.PetShortDTO;
import net.dzioba.petclinic.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class PetShortMapperDecorator implements PetShortMapper {

    private PetShortMapper petShortMapper;

    @Autowired
    @Qualifier("delegate")
    public void setPetShortMapper(PetShortMapper petShortMapper) {
        this.petShortMapper = petShortMapper;
    }

    public PetShortMapper getPetShortMapper() {
        return petShortMapper;
    }

    @Override
    public PetShortDTO petToPetShortDTO(Pet pet) {
        PetShortDTO petShortDTO = petShortMapper.petToPetShortDTO(pet);
        petShortDTO.setDetailsUrl(OwnerDTOController.BASE_URL + "/" + pet.getOwner().getId() + "/pets/" + pet.getId());
        return petShortDTO;
    }
}
