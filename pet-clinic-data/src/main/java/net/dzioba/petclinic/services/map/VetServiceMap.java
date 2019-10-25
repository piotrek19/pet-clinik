package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VetServiceMap extends CrudServiceMap<Vet> implements VetService {

    @Override
    public Vet save(Vet vet){
        Objects.requireNonNull(vet);
        Objects.requireNonNull(vet.getLastName());
        return super.save(vet);
    }
}
