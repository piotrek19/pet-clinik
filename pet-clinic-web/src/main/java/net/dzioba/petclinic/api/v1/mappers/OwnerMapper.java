package net.dzioba.petclinic.api.v1.mappers;

import net.dzioba.petclinic.api.v1.model.OwnerDTO;
import net.dzioba.petclinic.model.Owner;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(OwnerMapperDecorator.class)
public interface OwnerMapper {

    @Mapping(target = "pets", ignore = true)
    OwnerDTO ownerToOwnerDTO(Owner owner);
}
