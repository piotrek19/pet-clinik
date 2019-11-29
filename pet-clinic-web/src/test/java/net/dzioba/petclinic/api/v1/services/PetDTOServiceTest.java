package net.dzioba.petclinic.api.v1.services;

import net.dzioba.petclinic.api.v1.mappers.PetMapper;
import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.api.v1.model.PetTypeDTO;
import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.services.OwnerService;
import net.dzioba.petclinic.services.PetService;
import net.dzioba.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetDTOServiceTest {

    private static final Long PET1_ID = 11L;
    private static final String PET1_NAME = "PET1_NAME";
    private static final Long OWNER_ID = 11L;
    private static final Long PET_TYPE_ID = 21L;

    @InjectMocks
    private PetDTOService petDTOService;    // class under test

    @Mock
    private PetService petService;
    @Mock
    private PetMapper petMapper;

    @Mock
    private OwnerService ownerService;
    @Mock
    private PetTypeService petTypeService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void findById() {
        Pet pet = new Pet();
        pet.setId(PET1_ID);
        pet.setName(PET1_NAME);

        PetDTO petDTO = new PetDTO();
        petDTO.setId(PET1_ID);
        petDTO.setName(PET1_NAME);

        //given
        when(petService.findById(PET1_ID)).thenReturn(pet);
        when(petMapper.petToPetDTO(pet)).thenReturn(petDTO);

        //when
        PetDTO resultPetDTO = petDTOService.findById(PET1_ID);

        //then
        assertThat(resultPetDTO).isEqualTo(petDTO);
        verify(petService, only()).findById(PET1_ID);
        verify(petMapper, only()).petToPetDTO(pet);

    }

    @Test
    void save() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName(PET1_NAME);
        petDTO.setPetType(new PetTypeDTO(PET_TYPE_ID, "dog"));

        PetType petType = PetType.builder().id(PET_TYPE_ID).name("dog").build();

        Owner owner = Owner.builder().id(OWNER_ID).build();

        Pet pet = new Pet();
        pet.setName(PET1_NAME);
        pet.setPetType(petType);

        Pet petBeforeSave = new Pet();
        petBeforeSave.setName(PET1_NAME);
        petBeforeSave.setPetType(petType);
        petBeforeSave.setOwner(owner);

        Pet savedPet = new Pet();
        savedPet.setId(PET1_ID);
        savedPet.setName(PET1_NAME);
        savedPet.setPetType(petType);
        savedPet.setOwner(owner);

        PetDTO resultPetDTO = new PetDTO();
        resultPetDTO.setId(PET1_ID);
        resultPetDTO.setName(PET1_NAME);

        //given
        when(petMapper.petDTOToPet(petDTO)).thenReturn(pet);
        when(ownerService.findById(OWNER_ID)).thenReturn(owner);
        when(petTypeService.findById(PET_TYPE_ID)).thenReturn(petType);
        when(petService.save(petBeforeSave)).thenReturn(savedPet);
        when(petMapper.petToPetDTO(savedPet)).thenReturn(resultPetDTO);

        //when
        PetDTO result = petDTOService.save(petDTO, OWNER_ID);

        //then
        assertThat(result).isEqualTo(resultPetDTO);

        verify(petMapper, times(1)).petDTOToPet(any());
        verify(ownerService, times(1)).findById(OWNER_ID);
        verify(petTypeService, times(1)).findById(PET_TYPE_ID);
        verify(petService, only()).save(petBeforeSave);
        verify(petMapper, times(1)).petToPetDTO(any());
    }

    @Test
    void deleteById() {
        //when
        petDTOService.deleteById(PET1_ID);
        //then
        verify(petService, only()).deleteById(PET1_ID);
    }
}