package net.dzioba.petclinic.model;

import java.util.HashSet;
import java.util.Set;

public class Vet extends Person {

    private Set<Speciality> specialities = new HashSet<>();

    public Vet() {
    }

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    public Speciality addSpeciality(Speciality speciality) {
        specialities.add(speciality);
        return speciality;
    }
}
