package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OwnerServiceMapTest {

    private static final long OWNER_ID = 1L;
    private static final String OWNER_ADDRESS = "OWNER_ADDRESS";
    private static final String OWNER_FIRST_NAME = "OWNER_FIRST_NAME";
    private static final String OWNER_LAST_NAME = "OWNER_LAST_NAME";
    private static final String PET_TYPE_NAME = "PET_TYPE_NAME";

    private OwnerServiceMap ownerServiceMap;

    private PetServiceMap petServiceMap;
    private PetTypeServiceMap petTypeServiceMap;

    @BeforeEach
    void setUp() {
        petServiceMap = new PetServiceMap();
        petTypeServiceMap = new PetTypeServiceMap();
        ownerServiceMap = new OwnerServiceMap(petServiceMap, petTypeServiceMap);
    }

    private Owner createReferenceOwner() {
        Owner owner = new Owner();
        owner.setId(OWNER_ID);
        owner.setAddress(OWNER_ADDRESS);
        owner.setFirstName(OWNER_FIRST_NAME);
        owner.setLastName(OWNER_LAST_NAME);
        return owner;
    }

    @Test
    void findAll() {
        //given
        Owner referenceOwner = ownerServiceMap.save(createReferenceOwner());
        //when
        Set<Owner> resultSet =  ownerServiceMap.findAll();
        //then
        assertThat(resultSet).containsOnly(referenceOwner);
    }

    @Test
    void findAllWithEmptyResponse() {
        //when
        Set<Owner> resultSet =  ownerServiceMap.findAll();
        //then
        assertThat(resultSet).isEmpty();
    }

    @Test
    void findById() {
        //given
        Owner referenceOwner = createReferenceOwner();
        ownerServiceMap.save(referenceOwner);
        //when
        Owner resultOwner =  ownerServiceMap.findById(OWNER_ID);
        //then
        assertThat(resultOwner).isSameAs(referenceOwner);
    }

    @Test
    void findByIdWithNullId() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceMap.findById(null);
        });
    }

    @Test
    void deleteById() {
        //given
        Owner referenceOwner = ownerServiceMap.save(createReferenceOwner());
        //when
        ownerServiceMap.deleteById(referenceOwner.getId());
        //then
        assertThat(ownerServiceMap.findAll()).isEmpty();
    }

    @Test
    void deleteByIdWithNullId() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceMap.deleteById(null);
        });
    }

    @Test
    void delete() {
        //given
        Owner referenceOwner = ownerServiceMap.save(createReferenceOwner());
        //when
        ownerServiceMap.delete(referenceOwner);
        //then
        assertThat(ownerServiceMap.findAll()).isEmpty();
    }

    @Test
    void deleteWithNullArgument() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceMap.delete(null);
        });
    }

    @Test
    void findByLastName() {
        //given
        Owner referenceOwner = ownerServiceMap.save(createReferenceOwner());
        //when
        Owner resultOwner = ownerServiceMap.findByLastName(OWNER_LAST_NAME);
        //then
        assertThat(resultOwner).isSameAs(referenceOwner);
    }

    @Test
    void findByLastNameWithNullArgument() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceMap.findByLastName(null);
        });
    }

    @Test
    void save() {
        //given
        Owner owner = createReferenceOwner();
        owner.setId(null);
        //when
        Owner resultOwner = ownerServiceMap.save(owner);
        owner.setId(resultOwner.getId());
        //then
        assertThat(resultOwner.getId()).isNotNull();
        assertThat(resultOwner).isEqualTo(owner);
    }

    @Test
    void saveWithUnsavedPetAndPetType() {
        //given
        Owner owner = createReferenceOwner();
        owner.setId(null);
        PetType petType = new PetType(PET_TYPE_NAME);
        Pet pet = new Pet("PET_NAME", LocalDate.now(), petType, owner);
        owner.addPet(pet);
        //when
        Owner resultOwner = ownerServiceMap.save(owner);
        owner.setId(resultOwner.getId());
        petType.setId(petTypeServiceMap.findAll().iterator().next().getId());
        pet.setId(petServiceMap.findAll().iterator().next().getId());
        //then
        assertThat(resultOwner.getId()).isNotNull();
        assertThat(resultOwner).isEqualTo(owner);
        assertThat(petTypeServiceMap.findAll().iterator().next().getId()).isNotNull();
        assertThat(petTypeServiceMap.findAll().iterator().next()).isEqualTo(petType);
        assertThat(petServiceMap.findAll().iterator().next().getId()).isNotNull();
        assertThat(petServiceMap.findAll().iterator().next()).isEqualTo(pet);
    }

    @Test
    void saveWithNullArgument() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceMap.save(null);
        });
    }

    @Test
    void saveWithNullLastName() {
        //given
        Owner owner = createReferenceOwner();
        owner.setLastName(null);
        // when then
        assertThrows(NullPointerException.class, () -> {
            ownerServiceMap.save(owner);
        });
    }

    @Test
    void saveWithNullAddress() {
        //given
        Owner owner = createReferenceOwner();
        owner.setAddress(null);
        // when then
        assertThrows(NullPointerException.class, () -> {
            ownerServiceMap.save(owner);
        });
    }
}