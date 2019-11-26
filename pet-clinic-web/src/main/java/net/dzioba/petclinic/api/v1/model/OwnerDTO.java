package net.dzioba.petclinic.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String telephone;
    private Set<PetShortDTO> pets = new HashSet<>();

}
