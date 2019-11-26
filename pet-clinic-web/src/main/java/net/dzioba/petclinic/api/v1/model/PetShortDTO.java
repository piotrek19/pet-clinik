package net.dzioba.petclinic.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetShortDTO {

    private String name;
    private String detailsUrl;

}
