package net.dzioba.petclinic.controllers;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private static final Long OWNER_ID = 11L;
    private static final Long PET_ID = 11L;

    @InjectMocks
    PetController petController;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    private Owner createReferenceOwner(){
        Owner owner = new Owner();
        owner.setId(OWNER_ID);
        return owner;
    }

    private Pet createReferencePet() {
        Pet pet = new Pet();
        pet.setId(PET_ID);
        return pet;
    }

    private Set<PetType> createReferencePetTypes(){
        return Set.of(new PetType("dog"), new PetType("cat"));
    }

    @Test
    void createPetForm() throws Exception {
        when(ownerService.findById(OWNER_ID)).thenReturn(createReferenceOwner());
        when(petTypeService.findAll()).thenReturn(createReferencePetTypes());

        mockMvc.perform(get("/owners/" + OWNER_ID + "/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("petTypes"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        verify(ownerService, times(1)).findById(any());
        verify(petTypeService, times(1)).findAll();
    }

    @Test
    void createPet() throws Exception {
        when(ownerService.findById(OWNER_ID)).thenReturn(createReferenceOwner());
        when(petService.save(any())).thenReturn(createReferencePet());

        mockMvc.perform(post("/owners/" + OWNER_ID + "/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + OWNER_ID));

        verify(petService, times(1)).save(any());
        verify(ownerService, times(2)).findById(any());
    }

    @Test
    void editPetForm() throws Exception {
        when(ownerService.findById(OWNER_ID)).thenReturn(createReferenceOwner());
        when(petService.findById(PET_ID)).thenReturn(createReferencePet());
        when(petTypeService.findAll()).thenReturn(createReferencePetTypes());

        mockMvc.perform(get("/owners/" + OWNER_ID + "/pets/" + PET_ID + "/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("petTypes"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        verify(ownerService, times(1)).findById(any());
        verify(petService, times(1)).findById(any());
        verify(petTypeService, times(1)).findAll();
    }

    @Test
    void editPet() {
    }
}