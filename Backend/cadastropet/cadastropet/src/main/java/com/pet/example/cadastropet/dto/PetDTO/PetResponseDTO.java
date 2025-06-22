package com.pet.example.cadastropet.dto.PetDTO;


import com.pet.example.cadastropet.enums.Especie;
import lombok.Data;

import java.time.LocalDate;


// GET
@Data
public class PetResponseDTO {

    private Long id;
    private String nome;
    private Especie especie;
    private String raca;
    private LocalDate dataNascimento;
    private Double peso;
    private Long donoId;

}

