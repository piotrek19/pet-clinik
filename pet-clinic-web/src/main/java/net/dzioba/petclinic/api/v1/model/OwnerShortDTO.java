package net.dzioba.petclinic.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerShortDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String detailsUrl;

}