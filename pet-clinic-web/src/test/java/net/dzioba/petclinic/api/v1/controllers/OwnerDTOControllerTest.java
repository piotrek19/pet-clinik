package net.dzioba.petclinic.api.v1.controllers;

import net.dzioba.petclinic.api.v1.model.OwnerDTO;
import net.dzioba.petclinic.api.v1.services.OwnerDTOService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OwnerDTOControllerTest {

    private static final Long OWNER1_ID = 11L;
    private static final String OWNER1_LASTNAME = "LNAME11";
    private static final Long OWNER2_ID = 12L;
    private static final String OWNER2_LASTNAME = "LNAME12";

    @InjectMocks
    private OwnerDTOController ownerDTOController; //class under test

    @Mock
    private OwnerDTOService ownerDTOService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerDTOController).build();
    }

    @Test
    void listOwners() throws Exception {
        //given
        when(ownerDTOService.findAll())
                .thenReturn(createReferenceOwners());

        //when then
        mockMvc.perform(get(OwnerDTOController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owners", hasSize(2)));

        verify(ownerDTOService, times(1)).findAll();

    }

    private List<OwnerDTO> createReferenceOwners() {
        OwnerDTO owner1 = new OwnerDTO();
        owner1.setId(OWNER1_ID);
        owner1.setLastName(OWNER1_LASTNAME);
        OwnerDTO owner2 = new OwnerDTO();
        owner2.setId(OWNER2_ID);
        owner2.setLastName(OWNER2_LASTNAME);

        return List.of(owner1, owner2);
    }

    @Test
    void getOwnerById() throws Exception {
        //given
        when(ownerDTOService.findById(OWNER1_ID))
                .thenReturn(createReferenceOwners().get(0));

        //when then
        mockMvc.perform(get(OwnerDTOController.BASE_URL + "/" + OWNER1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((Integer.valueOf(OWNER1_ID.toString())))))
                .andExpect(jsonPath("$.lastName", equalTo(OWNER1_LASTNAME)));

        verify(ownerDTOService, times(1)).findById(any());
    }

    @Test
    void testGetOwnerByLastName() throws Exception {
        //given
        when(ownerDTOService.findByLastName("%" + OWNER2_LASTNAME + "%"))
                .thenReturn(List.of(createReferenceOwners().get(1)));

        //when then
        mockMvc.perform(get(OwnerDTOController.BASE_URL + "/lastname/" + OWNER2_LASTNAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owners", hasSize(1)))
                .andExpect(jsonPath("$.owners[0].id", equalTo((Integer.valueOf(OWNER2_ID.toString())))))
                .andExpect(jsonPath("$.owners[0].lastName", equalTo(OWNER2_LASTNAME)));

        verify(ownerDTOService, times(1)).findByLastName(any());
    }
}