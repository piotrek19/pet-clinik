package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Speciality;
import net.dzioba.petclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
@Profile({"default", "map"})
public class SpecialityServiceMap extends CrudServiceMap<Speciality> implements SpecialityService {

    @Override
    public Speciality save(Speciality speciality) {
        requireNonNull(speciality);
        requireNonNull(speciality.getDescription());
        return super.save(speciality);
    }
}
