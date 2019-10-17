package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VetServiceMap extends CrudServiceMap<Vet, Long> implements VetService {

    @Override
    public Vet save(Vet object) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(object.getId());

        return save(object.getId(), object);
    }
}
