package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.services.OwnerService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class OwnerServiceMap extends CrudServiceMap<Owner> implements OwnerService {

    @Override
    public Owner findByLastName(String lastName) {
        Objects.requireNonNull(lastName);

        Collection<Owner> values = map.values();
        for (Owner value : values) {
            if (lastName.equals(value.getLastName()))
                return value;   // Owner found.
        }
        // Owner not found:
        return null;
    }

}
