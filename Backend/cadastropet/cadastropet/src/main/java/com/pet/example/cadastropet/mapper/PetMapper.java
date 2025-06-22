package com.pet.example.cadastropet.mapper;


import com.pet.example.cadastropet.dto.PetDTO.PetRequestDTO;
import com.pet.example.cadastropet.dto.PetDTO.PetResponseDTO;
import com.pet.example.cadastropet.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public Pet toEntity(PetRequestDTO dto) {
        Pet pet = new Pet();
        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setDataNascimento(dto.getDataNascimento());
        pet.setPeso(dto.getPeso());
        // dono será setado no Service após buscar o Dono por ID
        return pet;
    }

    public PetResponseDTO toDTO(Pet pet) {
        PetResponseDTO dto = new PetResponseDTO();
        dto.setId(pet.getId());
        dto.setNome(pet.getNome());
        dto.setEspecie(pet.getEspecie());
        dto.setRaca(pet.getRaca());
        dto.setDataNascimento(pet.getDataNascimento());
        dto.setPeso(pet.getPeso());
        dto.setDonoId(pet.getDono() != null ? pet.getDono().getId() : null);
        return dto;
    }
}

