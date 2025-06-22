package com.pet.example.cadastropet.service;

import com.pet.example.cadastropet.dto.PetDTO.PetRequestDTO;
import com.pet.example.cadastropet.dto.PetDTO.PetResponseDTO;
import com.pet.example.cadastropet.mapper.PetMapper;
import com.pet.example.cadastropet.model.Dono;
import com.pet.example.cadastropet.model.Pet;
import com.pet.example.cadastropet.repository.DonoRepository;
import com.pet.example.cadastropet.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // Injeta automaticamente os campos final pelo construtor
public class PetService {

    private final PetRepository petRepository;    // Repositório para CRUD de Pet
    private final DonoRepository donoRepository;  // Repositório para buscar o Dono (relacionamento)
    private final PetMapper petMapper;            // Mapper para converter DTO <-> Entity

    /**
     * Método para salvar um novo Pet, vinculando ao Dono pelo id.
     *
     * @param dto Dados do pet (PetRequestDTO) que inclui donoId para relacionamento.
     * @return PetResponseDTO com dados do pet salvo, incluindo ID gerado.
     */
    public PetResponseDTO salvarPet(PetRequestDTO dto) {
        // Busca o dono pelo id enviado no DTO; lança exceção se não encontrado.
        Dono dono = donoRepository.findById(dto.getDonoId())
                .orElseThrow(() -> new EntityNotFoundException("Dono não encontrado"));

        // Converte DTO para entidade Pet.
        Pet pet = petMapper.toEntity(dto);

        // Define o dono da entidade Pet para manter o relacionamento no banco.
        pet.setDono(dono);

        // Salva o pet no banco de dados (insert ou update).
        Pet salvo = petRepository.save(pet);

        // Converte a entidade salva de volta para DTO de resposta.
        return petMapper.toDTO(salvo);
    }

    /**
     * Lista todos os pets cadastrados.
     *
     * @return Lista de PetResponseDTO convertidos.
     */
    public List<PetResponseDTO> listarPets() {
        // Busca todos os pets do banco.
        return petRepository.findAll()
                .stream()
                // Converte cada entidade Pet para DTO de resposta.
                .map(petMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PetResponseDTO buscarPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com id: " + id));
        return petMapper.toDTO(pet);
    }


    /**
     * Deleta um pet pelo seu ID.
     *
     * @param id ID do pet a ser removido.
     */
    public void deletarPet(Long id) {
        // Deleta o pet no banco pelo ID (se não existir, não lança erro).
        petRepository.deleteById(id);
    }

    /**
     * Atualiza dados de um pet existente.
     *
     * @param id ID do pet que será atualizado.
     * @param dto Novos dados para atualização (PetRequestDTO).
     * @return DTO atualizado com os dados do pet salvo.
     * @throws EntityNotFoundException se o pet ou dono não forem encontrados.
     */
    public PetResponseDTO alterarPet(Long id, PetRequestDTO dto) {
        // Busca o pet pelo ID, erro se não encontrado.
        Pet petExistente = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado"));

        // Busca o dono pelo ID para garantir relacionamento válido.
        Dono dono = donoRepository.findById(dto.getDonoId())
                .orElseThrow(() -> new EntityNotFoundException("Dono não encontrado"));

        // Atualiza os campos do pet com os dados recebidos no DTO.
        petExistente.setNome(dto.getNome());
        petExistente.setEspecie(dto.getEspecie());
        petExistente.setRaca(dto.getRaca());
        petExistente.setDataNascimento(dto.getDataNascimento());
        petExistente.setPeso(dto.getPeso());

        // Atualiza o dono relacionado do pet.
        petExistente.setDono(dono);

        // Salva as alterações no banco.
        Pet atualizado = petRepository.save(petExistente);

        // Converte a entidade atualizada para DTO e retorna.
        return petMapper.toDTO(atualizado);
    }
}





