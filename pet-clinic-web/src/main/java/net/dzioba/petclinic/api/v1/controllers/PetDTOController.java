package net.dzioba.petclinic.api.v1.controllers;

import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.api.v1.services.PetDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/owners/{ownerId}/pets")
public class PetDTOController {

    private PetDTOService petDTOService;

    @Autowired
    public PetDTOController(PetDTOService petDTOService) {
        this.petDTOService = petDTOService;
    }

    @GetMapping("/{petId}")
    ResponseEntity<PetDTO> findById(@PathVariable Long ownerId, @PathVariable Long petId){
        return new ResponseEntity<>(
                petDTOService.findById(petId), HttpStatus.OK);
    }
}
