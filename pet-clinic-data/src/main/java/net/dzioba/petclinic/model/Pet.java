package net.dzioba.petclinic.model;

import java.time.LocalDate;

public class Pet extends BaseEntity {

    private String name;
    private LocalDate birthDate;
    private PetType petType;
    private Owner owner;

    public Pet() {
    }

    public Pet(String name, LocalDate birthDate, PetType petType, Owner owner) {
        this.name = name;
        this.birthDate = birthDate;
        this.petType = petType;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
