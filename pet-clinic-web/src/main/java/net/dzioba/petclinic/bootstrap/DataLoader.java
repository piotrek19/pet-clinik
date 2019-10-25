package net.dzioba.petclinic.bootstrap;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.services.OwnerService;
import net.dzioba.petclinic.services.PetTypeService;
import net.dzioba.petclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

        Pet michaelsDog = new Pet();
        michaelsDog.setBirthDate(LocalDate.now());
        michaelsDog.setName("Suzu");
        michaelsDog.setPetType(dogPetType);

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("Street 132, City");
        owner1.setTelephone("123432123");
        owner1.getPets().add(michaelsDog);
        ownerService.save(owner1);

        Pet fionasCat = new Pet();
        fionasCat.setBirthDate(LocalDate.now());
        fionasCat.setName("Chiki");
        fionasCat.setPetType(catPetType);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("Street 74, City");
        owner2.setTelephone("53223423");
        owner2.getPets().add(fionasCat);
        ownerService.save(owner2);

        System.out.println("Loaded: Owners and Pets...");

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
