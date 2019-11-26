package net.dzioba.petclinic.api.v1.mappers;

import net.dzioba.petclinic.api.v1.model.PetShortDTO;
import net.dzioba.petclinic.model.Pet;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(PetShortMapperDecorator.class)
public interface PetShortMapper {

    PetShortDTO petToPetShortDTO(Pet pet);
}
