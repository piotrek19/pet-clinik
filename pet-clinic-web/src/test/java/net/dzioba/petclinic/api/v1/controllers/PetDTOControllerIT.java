package net.dzioba.petclinic.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dzioba.petclinic.api.v1.model.OwnerShortDTO;
import net.dzioba.petclinic.api.v1.model.PetDTO;
import net.dzioba.petclinic.api.v1.model.PetTypeDTO;
import net.dzioba.petclinic.api.v1.services.PetDTOService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(PetDTOController.class)
class PetDTOControllerIT {

    @MockBean
    private PetDTOService petDTOService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // REST DOCS common documentation artifacts:
    private static ConstrainedFields fields = new ConstrainedFields(PetDTO.class);

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

    private static final ParameterDescriptor ownerParameterDescriptor =
            parameterWithName("ownerId").description("Id of Owner which has Pets");

    private static final ParameterDescriptor petParameterDescriptor =
            parameterWithName("petId").description("Id of Pet");

    private static final PathParametersSnippet ownerPathParametersSnippet = pathParameters(ownerParameterDescriptor);

    private static final PathParametersSnippet petPathParametersSnippet = pathParameters(
            ownerParameterDescriptor, petParameterDescriptor);

    private static final FieldDescriptor[] petDTOResponseFieldsDescriptors = new FieldDescriptor[] {
            fields.withPath("id").description("Pet id"),
            fields.withPath("name").description("Pet name"),
            fields.withPath("birthDate").description("Pet birth date"),
            fields.withPath("petType").description("PetType"),
            fields.withPath("petType.id").description("Id of PetType"),
            fields.withPath("petType.name").description("PetType name"),
            fields.withPath("owner").description("Pet Owner"),
            fields.withPath("owner.id").description("Owner id"),
            fields.withPath("owner.firstName").description("Owner fist name"),
            fields.withPath("owner.lastName").description("Owner last name"),
            fields.withPath("owner.detailsUrl").description("URL to GET details of the Owner"),
            fields.withPath("visits[]").description("Array of Pet Visits")
    };

    private static ResponseFieldsSnippet petDTOResponseFieldsSnippet = responseFields(petDTOResponseFieldsDescriptors);

    // HELPER methods:

    private static final Long PET1_ID = 2L;
    private static final String PET1_NAME = "PetName";
    private static final Long OWNER_ID = 1L;

    private static PetDTO getNewPetDTO() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("PetName");
        petDTO.setBirthDate(LocalDate.of(2015, 1,15));
        PetTypeDTO petTypeDTO = new PetTypeDTO(1L, null);
        petDTO.setPetType(petTypeDTO);
        return petDTO;
    }

    private static PetDTO getPersistedPetDTO() {
        PetDTO resultPetDTO = new PetDTO();
        resultPetDTO.setId(PET1_ID);
        resultPetDTO.setName(PET1_NAME);
        resultPetDTO.setBirthDate(LocalDate.of(2015, 1,15));
        PetTypeDTO resultPetTypeDTO = new PetTypeDTO(1L, "dog");
        OwnerShortDTO resultOwnerShortDTO = new OwnerShortDTO(
                OWNER_ID, "OwnerFirstName", "OwnerLastName", "localhost:8080/api/v1/owners/" + OWNER_ID);
        resultPetDTO.setOwner(resultOwnerShortDTO);
        resultPetDTO.setPetType(resultPetTypeDTO);
        return resultPetDTO;
    }

    // TESTS:
    @Test
    void findPetsOfGivenOwner() throws Exception {
        //given
        when(petDTOService.findPetsOfGivenOwner(OWNER_ID))
                .thenReturn(List.of(getPersistedPetDTO()));

        //when then
        mockMvc.perform(get(PetDTOController.BASE_URL, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pets", hasSize(1)))
                .andDo(document("v1/owners/ownerId/pets-getMany",
                        ownerPathParametersSnippet,
                        responseFields(fieldWithPath("pets[]").description("Array containing all Pets of given Owner"))
                                .andWithPrefix("pets[].", petDTOResponseFieldsDescriptors))
                );

        verify(petDTOService, times(1)).findPetsOfGivenOwner(any());

    }

    @Test
    void findById() throws Exception {

        //given
        when(petDTOService.findById(PET1_ID))
                .thenReturn(getPersistedPetDTO());

        //when then
        mockMvc.perform(get(PetDTOController.BASE_URL + "/{petId}", OWNER_ID, PET1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((Integer.valueOf(PET1_ID.toString())))))
                .andExpect(jsonPath("$.name", equalTo(PET1_NAME)))
                .andDo(document("v1/owners/ownerId/pets-get",
                        petPathParametersSnippet,
                        petDTOResponseFieldsSnippet
                ));


        verify(petDTOService, times(1)).findById(any());
    }

    @Test
    void findByIdAndNotFound() throws Exception {

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
    void createPet() throws Exception {

        PetDTO petDTO = getNewPetDTO();

        PetDTO resultPetDTO = getPersistedPetDTO();

        //given
        when(petDTOService.save(petDTO, OWNER_ID)).thenReturn(resultPetDTO);

        //when then
        mockMvc.perform(post(PetDTOController.BASE_URL, OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo((Integer.valueOf(PET1_ID.toString())))))
                .andDo(document("v1/owners/ownerId/pets-create",
                        ownerPathParametersSnippet,
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("name").description("Pet name"),
                                fields.withPath("birthDate").description("Pet birth date"),
                                fields.withPath("petType").description("Pet type"),
                                fields.withPath("petType.id").description("Id of PetType"),
                                fields.withPath("petType.name").ignored(),
                                fields.withPath("owner").ignored(),
                                fields.withPath("visits[]").ignored()
                        ),
                        petDTOResponseFieldsSnippet
                ));

        verify(petDTOService, times(1)).save(any(), any());
    }

    @Test
    void deletePet() throws Exception {

        //when then
        mockMvc.perform(delete(PetDTOController.BASE_URL + "/{petId}", OWNER_ID, PET1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/owners/ownerId/pets-delete",
                        petPathParametersSnippet
                ));

        verify(petDTOService, times(1)).deleteById(PET1_ID);
    }

}