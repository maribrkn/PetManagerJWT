package com.pet.example.cadastropet.dto.PetDTO;

import com.pet.example.cadastropet.enums.Especie;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetRequestDTO {

    @NotBlank(message = "Nome do pet é obrigatório")
    private String nome;

    @NotNull(message = "Espécie é obrigatória")
    private Especie especie;

    @NotBlank(message = "Raça é obrigatória")
    private String raca;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que 0")
    private Double peso;

    @NotNull(message = "ID do dono é obrigatório")
    private Long donoId;
}


