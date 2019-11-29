package net.dzioba.petclinic.api.v1.controllers;

import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.api.v1.services.PetDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PetDTOController.BASE_URL)
public class PetDTOController {

    public static final String BASE_URL = "/api/v1/owners/{ownerId}/pets";

    private final PetDTOService petDTOService;

    @Autowired
    public PetDTOController(PetDTOService petDTOService) {
        this.petDTOService = petDTOService;
    }

    @GetMapping("/{petId}")
    @ResponseStatus(HttpStatus.OK)
    public PetDTO findById(@PathVariable Long petId){

        return petDTOService.findById(petId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDTO createPet(@RequestBody PetDTO petDTO, @PathVariable Long ownerId){
        return petDTOService.save(petDTO, ownerId);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePet(@PathVariable Long petId){
        petDTOService.deleteById(petId);
    }
}
