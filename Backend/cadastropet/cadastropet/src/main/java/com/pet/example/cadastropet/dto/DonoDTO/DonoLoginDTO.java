package com.pet.example.cadastropet.dto.DonoDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DonoLoginDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;
}
