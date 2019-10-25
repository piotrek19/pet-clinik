package net.dzioba.petclinic.bootstrap;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.services.OwnerService;
import net.dzioba.petclinic.services.PetTypeService;
import net.dzioba.petclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dogPetType = new PetType();
        dogPetType.setName("dog");
        petTypeService.save(dogPetType);

        PetType catPetType = new PetType();
        catPetType.setName("cat");
        petTypeService.save(catPetType);

        System.out.println("Loaded: PetTypes...");

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        ownerService.save(owner2);

        System.out.println("Loaded: Owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vetService.save(vet2);

        System.out.println("Loaded: Vets...");
    }
}
