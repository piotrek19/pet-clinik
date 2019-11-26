package net.dzioba.petclinic.api.v1.mappers;

import net.dzioba.petclinic.api.v1.model.OwnerShortDTO;
import net.dzioba.petclinic.model.Owner;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(OwnerShortMapperDecorator.class)
public interface OwnerShortMapper {

    OwnerShortDTO ownerToOwnerShortDTO(Owner owner);

}
