package net.dzioba.petclinic.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private PetTypeDTO petType;
    private OwnerShortDTO owner;
    private Set<VisitDTO> visits = new HashSet<>();
}
