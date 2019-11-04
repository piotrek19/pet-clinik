package net.dzioba.petclinic.services.jpa;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJpaTest {

    private static final long OWNER_ID = 1L;
    private static final String OWNER_ADDRESS = "OWNER_ADDRESS";
    private static final String OWNER_FIRST_NAME = "OWNER_FIRST_NAME";
    private static final String OWNER_LAST_NAME = "OWNER_LAST_NAME";

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceJpa ownerServiceJpa;


    private Owner createReferenceOwner() {
        Owner owner = new Owner();
        owner.setId(OWNER_ID);
        owner.setAddress(OWNER_ADDRESS);
        owner.setFirstName(OWNER_FIRST_NAME);
        owner.setLastName(OWNER_LAST_NAME);
        return owner;
    }

    @Test
    void findByLastName() {
        //given
        Owner referenceOwner = createReferenceOwner();
        when(ownerRepository.findByLastNameLike(OWNER_LAST_NAME)).thenReturn(List.of(referenceOwner));
        //when
        List<Owner> resultCollection = ownerServiceJpa.findByLastNameLike(OWNER_LAST_NAME);
        //then
        assertThat(resultCollection.iterator().next()).isEqualTo(referenceOwner);
        verify(ownerRepository, times(1)).findByLastNameLike(any());
    }

    @Test
    void findByLastNameWithNullArgument() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceJpa.findByLastNameLike(null);
        });
    }

    @Test
    void findAll() {
        //given
        Owner owner1 = createReferenceOwner();
        Owner owner2 = createReferenceOwner();
        owner2.setAddress("different address");
        HashSet<Owner> referenceSet = new HashSet<>(Set.of(owner1, owner2));
        when(ownerRepository.findAll()).thenReturn(referenceSet);
        //when
        Set<Owner> resultSet = ownerServiceJpa.findAll();
        //then
        assertThat(resultSet).isEqualTo(referenceSet);
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void findAllWithEmptyResponse() {
        //when
        Set<Owner> resultSet =  ownerServiceJpa.findAll();
        //then
        assertThat(resultSet).isEmpty();
    }

    @Test
    void findById() {
        //given
        Owner referenceOwner = createReferenceOwner();
        when(ownerRepository.findById(OWNER_ID)).thenReturn(Optional.of(referenceOwner));
        //when
        Owner resultOwner = ownerServiceJpa.findById(OWNER_ID);
        //then
        assertThat(resultOwner).isEqualTo(referenceOwner);
        verify(ownerRepository, times(1)).findById(any());
    }

    @Test
    void findByIdWithNullArgument() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceJpa.findById(null);
        });
    }

    @Test
    void save() {
        //given
        Owner referenceOwner = createReferenceOwner();
        when(ownerRepository.save(referenceOwner)).thenReturn(referenceOwner);
        //when
        Owner resultOwner = ownerServiceJpa.save(referenceOwner);
        //then
        assertThat(resultOwner).isEqualTo(referenceOwner);
        verify(ownerRepository, times(1)).save(any());
    }

    @Test
    void saveWithNullArgument() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceJpa.save(null);
        });
    }

    @Test
    void saveWithNullLastName() {
        //given
        Owner owner = createReferenceOwner();
        owner.setLastName(null);
        // when then
        assertThrows(NullPointerException.class, () -> {
            ownerServiceJpa.save(owner);
        });
    }

    @Test
    void saveWithNullAddress() {
        //given
        Owner owner = createReferenceOwner();
        owner.setAddress(null);
        // when then
        assertThrows(NullPointerException.class, () -> {
            ownerServiceJpa.save(owner);
        });
    }

    @Test
    void deleteById() {
        //when
        ownerServiceJpa.deleteById(OWNER_ID);
        //then
        verify(ownerRepository, only()).deleteById(OWNER_ID);
    }

    @Test
    void deleteByIdWithNullId() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceJpa.deleteById(null);
        });
    }

    @Test
    void delete() {
        //given
        Owner referenceOwner = createReferenceOwner();
        //when
        ownerServiceJpa.delete(referenceOwner);
        //then
        verify(ownerRepository, only()).delete(referenceOwner);
    }

    @Test
    void deleteWithNullArgument() {
        assertThrows(NullPointerException.class, () -> {
            ownerServiceJpa.delete(null);
        });
    }
}