package net.dzioba.petclinic.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetShortDTO {

    @NotBlank
    @Size(max=255)
    private String name;

    @URL
    private String detailsUrl;

}
