package net.dzioba.petclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vet")
public class Vet extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_speciality",
            joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities = new HashSet<>();

    @Builder
    public Vet(Long id, String firstName, String lastName, Set<Speciality> specialities) {
        super(id, firstName, lastName);
        if (specialities != null){
            this.specialities = specialities;
        }
    }

    public Speciality addSpeciality(Speciality speciality) {
        specialities.add(speciality);
        return speciality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vet)) return false;
        if (!super.equals(o)) return false;
        Vet vet = (Vet) o;
        return Objects.equals(specialities, vet.specialities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialities);
    }

    @Override
    public String toString() {
        return "Vet{" +
                "id=" + getId() +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", specialities=" + specialities +
                '}';
    }
}
