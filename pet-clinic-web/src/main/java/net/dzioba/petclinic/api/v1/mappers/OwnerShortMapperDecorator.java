package net.dzioba.petclinic.api.v1.mappers;

import net.dzioba.petclinic.api.v1.controllers.OwnerDTOController;
import net.dzioba.petclinic.api.v1.model.OwnerShortDTO;
import net.dzioba.petclinic.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OwnerShortMapperDecorator implements OwnerShortMapper {

    private OwnerShortMapper ownerShortMapper;

    @Autowired
    @Qualifier("delegate")
    public void setOwnerShortMapper(OwnerShortMapper ownerShortMapper) {
        this.ownerShortMapper = ownerShortMapper;
    }

    public OwnerShortMapper getOwnerShortMapper() {
        return ownerShortMapper;
    }

    @Override
    public OwnerShortDTO ownerToOwnerShortDTO(Owner owner) {
        OwnerShortDTO ownerShortDTO = ownerShortMapper.ownerToOwnerShortDTO(owner);
        ownerShortDTO.setDetailsUrl(OwnerDTOController.BASE_URL+ "/" + owner.getId());
        return ownerShortDTO;
    }
}
