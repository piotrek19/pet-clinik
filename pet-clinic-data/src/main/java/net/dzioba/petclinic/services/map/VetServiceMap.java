package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.services.SpecialityService;
import net.dzioba.petclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
public class VetServiceMap extends CrudServiceMap<Vet> implements VetService {

    private SpecialityService specialityService;

    @Autowired
    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet save(Vet vet){
        requireNonNull(vet);
        requireNonNull(vet.getLastName());
        saveUnsavedSpecialitiesOf(vet);
        return super.save(vet);
    }

    private void saveUnsavedSpecialitiesOf(Vet vet) {
        vet.getSpecialities().forEach(speciality -> {
            if (isNull(speciality.getId())){
                specialityService.save(speciality);
            }
        });
    }
}
