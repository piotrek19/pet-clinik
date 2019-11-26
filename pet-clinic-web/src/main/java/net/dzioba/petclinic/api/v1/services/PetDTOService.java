package net.dzioba.petclinic.api.v1.services;

import net.dzioba.petclinic.api.v1.mappers.PetMapper;
import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetDTOService {

    private PetService petService;
    private PetMapper petMapper;

    @Autowired
    public PetDTOService(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    public PetDTO findById(Long id){
        return petMapper.petToPetDTO(petService.findById(id));
    }
}
