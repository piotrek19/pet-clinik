package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetServiceMap extends CrudServiceMap<Vet> implements VetService {
}
