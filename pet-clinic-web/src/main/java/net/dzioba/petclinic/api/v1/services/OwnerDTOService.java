package net.dzioba.petclinic.api.v1.services;

import net.dzioba.petclinic.api.v1.mappers.OwnerMapper;
import net.dzioba.petclinic.api.v1.model.OwnerDTO;
import net.dzioba.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerDTOService {

    private OwnerService ownerService;
    private OwnerMapper ownerMapper;

    @Autowired
    public OwnerDTOService(OwnerService ownerService, OwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    public List<OwnerDTO> findAll(){
        return ownerService.findAll().stream()
                .map(ownerMapper::ownerToOwnerDTO)
                .collect(Collectors.toList());
    }

    public List<OwnerDTO> findByLastName(String lastName){
        return ownerService.findByLastNameLike(lastName).stream()
                .map(ownerMapper::ownerToOwnerDTO)
                .collect(Collectors.toList());
    }

    public OwnerDTO findById(Long id){
        return ownerMapper.ownerToOwnerDTO(ownerService.findById(id));
    }
}
