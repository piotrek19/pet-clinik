package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OwnerServiceMapTest {

    private static final long OWNER_ID = 1L;
    private static final String OWNER_ADDRESS = "OWNER_ADDRESS";
    private static final String OWNER_FIRST_NAME = "OWNER_FIRST_NAME";
    private static final String OWNER_LAST_NAME = "OWNER_LAST_NAME";

    private OwnerServiceMap ownerServiceMap;

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetServiceMap(), new PetTypeServiceMap());
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

    @Test @Disabled
    void findByLastName() {
    }

    @Test @Disabled
    void save() {
    }
}