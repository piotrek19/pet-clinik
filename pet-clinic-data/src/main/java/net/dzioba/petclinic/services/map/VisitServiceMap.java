package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Visit;
import net.dzioba.petclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
@Profile({"default", "map"})
public class VisitServiceMap extends CrudServiceMap<Visit> implements VisitService {


    @Override
    public Visit save(Visit visit) {
        requireNonNull(visit);
        requireNonNull(visit.getDate());
        requireNonNull(visit.getPet());
        requireNonNull(visit.getPet().getId());
        requireNonNull(visit.getPet().getOwner());
        requireNonNull(visit.getPet().getOwner().getId());

        return super.save(visit);
    }
}
