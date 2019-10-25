package net.dzioba.petclinic.bootstrap;

import net.dzioba.petclinic.model.*;
import net.dzioba.petclinic.services.OwnerService;
import net.dzioba.petclinic.services.PetTypeService;
import net.dzioba.petclinic.services.SpecialityService;
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
    private final SpecialityService specialityService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (noDataInModel()){
            saveData();
        }
    }

    private boolean noDataInModel() {
        return petTypeService.findAll().size() == 0;
    }

    private void saveData() {
        PetType dogPetType = new PetType();
        dogPetType.setName("dog");
        dogPetType = petTypeService.save(dogPetType);

        PetType catPetType = new PetType();
        catPetType.setName("cat");
        catPetType = petTypeService.save(catPetType);

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
        owner1 = ownerService.save(owner1);

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
        owner2 = ownerService.save(owner2);

        System.out.println("Loaded: Owners and Pets...");

        Speciality radiologySpeciality = new Speciality();
        radiologySpeciality.setDescription("radiology");
        radiologySpeciality = specialityService.save(radiologySpeciality);

        Speciality surgerySpeciality = new Speciality();
        surgerySpeciality.setDescription("surgery");
        surgerySpeciality = specialityService.save(surgerySpeciality);

        Speciality dentistSpeciality = new Speciality();
        dentistSpeciality.setDescription("dentist");
        // intentional lack of save for dentistSpeciality here

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(surgerySpeciality);
        vet1 = vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(radiologySpeciality);
        vet2.getSpecialities().add(dentistSpeciality);
        vet2 = vetService.save(vet2);

        System.out.println("Loaded: Vets...");
    }
}
