package net.dzioba.petclinic.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.api.v1.services.PetDTOService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PetDTOControllerTest {

    private static final Long PET1_ID = 11L;
    private static final String PET1_NAME = "PET1_NAME";
    private static final Long PET2_ID = 12L;
    private static final String PET2_NAME = "PET2_NAME";
    private static final Long OWNER_ID = 11L;

    @InjectMocks
    private PetDTOController petDTOController; //class under test

    @Mock
    private PetDTOService petDTOService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petDTOController).build();
    }

    @Test
    void findPetsOfGivenOwner() throws Exception {
        //given
        when(petDTOService.findPetsOfGivenOwner(OWNER_ID))
                .thenReturn(createReferencePets());

        //when then
        mockMvc.perform(get(PetDTOController.BASE_URL, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pets", hasSize(2)));

        verify(petDTOService, times(1)).findPetsOfGivenOwner(any());

    }

    private List<PetDTO> createReferencePets() {
        PetDTO petDTO1 = new PetDTO();
        petDTO1.setId(PET1_ID);
        petDTO1.setName(PET1_NAME);

        PetDTO petDTO2 = new PetDTO();
        petDTO2.setId(PET2_ID);
        petDTO2.setName(PET2_NAME);

        return List.of(petDTO1, petDTO2);
    }

    @Test
    public void findById() throws Exception {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(PET1_ID);
        petDTO.setName(PET1_NAME);

        //given
        when(petDTOService.findById(PET1_ID))
                .thenReturn(petDTO);

        //when then
        mockMvc.perform(get(PetDTOController.BASE_URL + "/" + PET1_ID, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((Integer.valueOf(PET1_ID.toString())))))
                .andExpect(jsonPath("$.name", equalTo(PET1_NAME)));

        verify(petDTOService, times(1)).findById(any());
    }

    @Test
    public void findByIdAndNotFound() throws Exception {

        //given
        when(petDTOService.findById(PET1_ID)).thenReturn(null);

        //when then
        mockMvc.perform(get(PetDTOController.BASE_URL + "/" + PET1_ID, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(petDTOService, times(1)).findById(any());
    }

    @Test
    public void createPet() throws Exception {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(PET1_ID);
        petDTO.setName(PET1_NAME);

        //given
        when(petDTOService.save(petDTO, OWNER_ID))
                .thenReturn(petDTO);

        //when then
        mockMvc.perform(post(PetDTOController.BASE_URL, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(petDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo((Integer.valueOf(PET1_ID.toString())))))
                .andExpect(jsonPath("$.name", equalTo(PET1_NAME)));

        verify(petDTOService, times(1)).save(any(), any());
    }

    @Test
    public void deletePet() throws Exception {

        //when then
        mockMvc.perform(delete(PetDTOController.BASE_URL + "/" + PET1_ID, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(petDTOService, times(1)).deleteById(PET1_ID);

    }
}