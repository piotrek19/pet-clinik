package net.dzioba.petclinic.bootstrap;

import net.dzioba.petclinic.model.*;
import net.dzioba.petclinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
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
        michaelsDog = owner1.addPet(michaelsDog);
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
        fionasCat = owner2.addPet(fionasCat);
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
        vet1.addSpeciality(surgerySpeciality);
        vet1 = vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.addSpeciality(radiologySpeciality);
        vet2.addSpeciality(dentistSpeciality);
        vet2 = vetService.save(vet2);

        System.out.println("Loaded: Vets...");

        Visit visit1 = new Visit();
        visit1.setDate(LocalDateTime.of(2020, 10, 30, 13, 0));
        visit1.setDescription("Occasional visit");
        visit1.setPet(fionasCat);
        visit1 = visitService.save(visit1);

        Visit visit2 = new Visit();
        visit2.setDate(LocalDateTime.of(2021, 10, 30, 13, 0));
        visit2.setDescription("Another occasional visit");
        visit2.setPet(fionasCat);
        visit2 = visitService.save(visit2);

        Visit visit3 = new Visit();
        visit3.setDate(LocalDateTime.of(2019, 10, 14, 10, 30));
        visit3.setDescription("Not really know why");
        visit3.setPet(michaelsDog);
        visit3 = visitService.save(visit3);

        System.out.println("Loaded: Visits...");
    }
}
