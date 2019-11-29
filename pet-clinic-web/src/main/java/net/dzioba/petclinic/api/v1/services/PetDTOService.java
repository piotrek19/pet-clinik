package net.dzioba.petclinic.api.v1.services;

import net.dzioba.petclinic.api.v1.mappers.PetMapper;
import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.services.OwnerService;
import net.dzioba.petclinic.services.PetService;
import net.dzioba.petclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PetDTOService {

    private PetService petService;
    private PetMapper petMapper;

    private OwnerService ownerService;
    private PetTypeService petTypeService;

    @Autowired
    public PetDTOService(PetService petService, PetMapper petMapper, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petMapper = petMapper;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    public List<PetDTO> findPetsOfGivenOwner(Long ownerId) {
        return petService.findAll().stream()
                .filter(p -> p.getOwner().getId() == ownerId )  // todo: repository level filtering instead of retrieving all
                .map(petMapper::petToPetDTO)
                .collect(Collectors.toList());
    }

    public PetDTO findById(Long id){
        PetDTO petDTO = null;

        Pet retrievedPet = petService.findById(id);
        if (retrievedPet != null){
            petDTO = petMapper.petToPetDTO(retrievedPet);
        }
        return petDTO;
    }

    @Transactional
    public PetDTO save(PetDTO petDTO, Long ownerId){
        Pet pet = petMapper.petDTOToPet(petDTO);

        // retrieve owner and make object references:
        Owner retrievedOwner = ownerService.findById(ownerId);
        Objects.requireNonNull(retrievedOwner);

        retrievedOwner.addPet(pet);

        // retrieve petType and make object references:
        Long petTypeId = pet.getPetType().getId();
        Objects.requireNonNull(petTypeId);
        PetType retrievedPetType = petTypeService.findById(petTypeId);
        Objects.requireNonNull(retrievedPetType);

        pet.setPetType(retrievedPetType);

        // save pet:
        Pet savedPet = petService.save(pet);
        return petMapper.petToPetDTO(savedPet);
    }

    public void deleteById(Long id) {

        petService.deleteById(id);
    }
}
