package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.services.VetService;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class VetServiceMap extends CrudServiceMap<Vet> implements VetService {

    @Override
    public Vet save(Vet vet){
        requireNonNull(vet);
        requireNonNull(vet.getLastName());
        return super.save(vet);
    }
}
