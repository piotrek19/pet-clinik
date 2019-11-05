package net.dzioba.petclinic.controllers;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.services.OwnerService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    OwnerController ownerController;

    @Mock
    OwnerService ownerService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void showFindOwnersForm() throws Exception {
        //when then
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwnersForm"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void findOwnerByLastNameResult() throws Exception {
        //given
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setLastName("lastName1");
        when(ownerService.findByLastNameLike(any())).thenReturn(List.of(owner));

        //when then
        mockMvc.perform(get("/owners?lastName=lastName1"))
                .andExpect(model().attribute("owner", hasProperty("lastName", is("lastName1"))))
                .andExpect(status().isOk())
                .andExpect(model().attribute("owners", hasSize(1)))
                .andExpect(view().name("owners/index"));

        verify(ownerService, times(1)).findByLastNameLike(any());
    }

    @Test
    void findOwnerByLastNameWithResultOwnerNotFound() throws Exception {
        //given
        when(ownerService.findByLastNameLike(any())).thenReturn(Lists.emptyList());

        //when then
        mockMvc.perform(get("/owners?lastName=NotExistingOne"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwnersForm"));

        verify(ownerService, times(1)).findByLastNameLike(any());
    }

    @Test
    void findOwnerByLastNameResultForEmptyLastName() throws Exception {
        //given
        List<Owner> owners = createOwners();
        when(ownerService.findByLastNameLike(any())).thenReturn(owners);

        //when then
        mockMvc.perform(get("/owners?lastName="))
                .andExpect(model().attributeExists("owner"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("owners", hasSize(2)))
                .andExpect(view().name("owners/index"));

        verify(ownerService, times(1)).findByLastNameLike(any());
    }

    private List<Owner> createOwners(){
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setAddress("address1");
        owner1.setLastName("lastName1");
        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setAddress("address2");
        owner2.setLastName("lastName2");
        return List.of(owner1, owner2);
    }

    @Test
    void showOwnerDetails() throws Exception {
        //given
        Owner owner = new Owner();
        owner.setId(1L);
        when(ownerService.findById(1L)).thenReturn(owner);

        //when then
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));

        verify(ownerService, times(1)).findById(any());
    }

    @Test
    void showCreateOwnerForm() throws Exception {
        //when then
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attribute("owner", hasProperty("id", nullValue())));
    }
}