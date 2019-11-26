package net.dzioba.petclinic.api.v1.mappers;

import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.model.Pet;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(PetMapperDecorator.class)
public interface PetMapper {

    @Mapping(target = "owner", ignore = true)
    PetDTO petToPetDTO(Pet pet);

}
