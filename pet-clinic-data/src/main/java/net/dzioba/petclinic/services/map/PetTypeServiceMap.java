package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

@Service
public class PetTypeServiceMap extends CrudServiceMap<PetType> implements PetTypeService {
}
