package com.pet.example.cadastropet.mapper;

import com.pet.example.cadastropet.dto.DonoDTO.DonoRegisterDTO;
import com.pet.example.cadastropet.dto.DonoDTO.DonoResponseDTO;
import com.pet.example.cadastropet.model.Dono;
import org.springframework.stereotype.Component;

// Indica para o Spring que essa classe é um Bean gerenciado pelo container de IoC (Inversão de Controle).
// Ou seja, o Spring vai criar a instância dessa classe automaticamente, controlar seu ciclo de vida
// e permitir que ela seja injetada em outras classes com @Autowired ou via construtor.
@Component
public class DonoMapper {

    // Converte um DTO de registro (recebido da API, com dados do cliente) para uma entidade Dono (modelo do banco)
    public Dono toEntity(DonoRegisterDTO dto) {
        Dono dono = new Dono();                 // Cria uma nova instância da entidade Dono
        dono.setNome(dto.getNome());            // Passa o nome do DTO para a entidade
        dono.setEmail(dto.getEmail());          // Passa o email do DTO para a entidade
        dono.setSenha(dto.getSenha());          // Passa a senha do DTO para a entidade
        return dono;                            // Retorna a entidade pronta para ser salva no banco
    }

    // Converte a entidade Dono (modelo do banco) para um DTO de resposta (para enviar dados seguros para o cliente)
    public DonoResponseDTO toDTO(Dono dono) {
        DonoResponseDTO dto = new DonoResponseDTO(); // Cria uma nova instância do DTO de resposta
        dto.setId(dono.getId());                      // Passa o id do dono para o DTO
        dto.setNome(dono.getNome());                  // Passa o nome do dono para o DTO
        dto.setEmail(dono.getEmail());                // Passa o email do dono para o DTO
        // OBS: Não passa a senha no DTO de resposta para não expor dados sensíveis
        return dto;                                   // Retorna o DTO para ser enviado na resposta da API
    }
}


