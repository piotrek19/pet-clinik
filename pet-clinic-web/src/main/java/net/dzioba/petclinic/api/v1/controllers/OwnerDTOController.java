package net.dzioba.petclinic.api.v1.controllers;

import net.dzioba.petclinic.api.v1.model.OwnerListDTO;
import net.dzioba.petclinic.api.v1.services.OwnerDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/api/v1/owners")
public class OwnerDTOController {

    OwnerDTOService ownerDTOService;

    @Autowired
    public OwnerDTOController(OwnerDTOService ownerDTOService) {
        this.ownerDTOService = ownerDTOService;
    }

    @GetMapping
    ResponseEntity<OwnerListDTO> listOwners(){
        return new ResponseEntity<>(
                new OwnerListDTO(ownerDTOService.findAll()), HttpStatus.OK);
    }

}
