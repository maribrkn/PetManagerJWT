package com.pet.example.cadastropet.controller;

import com.pet.example.cadastropet.dto.PetDTO.PetRequestDTO;
import com.pet.example.cadastropet.dto.PetDTO.PetResponseDTO;
import com.pet.example.cadastropet.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")  // Base para endpoints relacionados aos pets
@RequiredArgsConstructor  // Injeta PetService automaticamente via construtor
public class PetController {

    private final PetService petService;

    /**
     * Cadastra um novo pet vinculado a um dono.
     * POST /pets
     * Recebe dados do pet (PetRequestDTO) e retorna dados do pet salvo (PetResponseDTO).
     */
    @PostMapping
    public ResponseEntity<PetResponseDTO> cadastrar(@RequestBody @Valid PetRequestDTO dto) {
        PetResponseDTO petSalvo = petService.salvarPet(dto);
        return ResponseEntity.ok(petSalvo);
    }

    /**
     * Busca um pet pelo ID.
     * GET /pets/{id}
     * Retorna os dados do pet (PetResponseDTO).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> buscarPorId(@PathVariable Long id) {
        PetResponseDTO pet = petService.buscarPorId(id);
        return ResponseEntity.ok(pet);
    }

    /**
     * Lista todos os pets cadastrados.
     * GET /pets
     * Retorna lista de PetResponseDTO.
     */
    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> listarTodos() {
        List<PetResponseDTO> pets = petService.listarPets();
        return ResponseEntity.ok(pets);
    }

    /**
     * Atualiza um pet existente pelo ID.
     * PUT /pets/{id}
     * Recebe novos dados do pet (PetRequestDTO) e retorna dados atualizados.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> atualizar(@PathVariable Long id,
                                                    @RequestBody @Valid PetRequestDTO dto) {
        PetResponseDTO atualizado = petService.alterarPet(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Deleta um pet pelo ID.
     * DELETE /pets/{id}
     * Retorna 204 No Content se deletado com sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletarPet(id);
        return ResponseEntity.noContent().build();
    }
}


