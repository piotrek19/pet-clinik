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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PetDTOControllerTest {

    private static final Long PET1_ID = 11L;
    private static final String PET1_NAME = "PET1_NAME";
    private static final Long OWNER_ID = 11L;

    @InjectMocks
    private PetDTOController petDTOController; //class under test

    @Mock
    private PetDTOService petDTOService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petDTOController).build();
    }

    @Test
    void findById() throws Exception {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(PET1_ID);
        petDTO.setName(PET1_NAME);

        //given
        when(petDTOService.findById(PET1_ID))
                .thenReturn(petDTO);

        //when then
        mockMvc.perform(get(PetDTOController.BASE_URL + "/" + PET1_ID, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((Integer.valueOf(PET1_ID.toString())))))
                .andExpect(jsonPath("$.name", equalTo(PET1_NAME)));

        verify(petDTOService, times(1)).findById(any());
    }

    @Test
    void createPet() throws Exception {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(PET1_ID);
        petDTO.setName(PET1_NAME);

        //given
        when(petDTOService.save(petDTO, OWNER_ID))
                .thenReturn(petDTO);

        //when then
        mockMvc.perform(post(PetDTOController.BASE_URL, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(petDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo((Integer.valueOf(PET1_ID.toString())))))
                .andExpect(jsonPath("$.name", equalTo(PET1_NAME)));

        verify(petDTOService, times(1)).save(any(), any());
    }
}