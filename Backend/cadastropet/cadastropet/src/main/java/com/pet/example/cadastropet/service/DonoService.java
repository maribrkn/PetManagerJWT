package com.pet.example.cadastropet.service;

import com.pet.example.cadastropet.dto.DonoDTO.DonoLoginDTO;
import com.pet.example.cadastropet.dto.DonoDTO.DonoRegisterDTO;
import com.pet.example.cadastropet.dto.DonoDTO.DonoResponseDTO;
import com.pet.example.cadastropet.dto.DonoDTO.LoginResponseDTO;
import com.pet.example.cadastropet.mapper.DonoMapper;
import com.pet.example.cadastropet.model.Dono;
import com.pet.example.cadastropet.repository.DonoRepository;
import com.pet.example.cadastropet.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // Injeta donoRepository, donoMapper, jwtUtil e passwordEncoder
public class DonoService {

    private final DonoRepository donoRepository;
    private final DonoMapper donoMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cadastra um novo dono (usuário) no sistema, validando se as senhas coincidem.
     */
    public DonoResponseDTO cadastrar(DonoRegisterDTO dto) {
        validarSenha(dto); // Verifica se senha e confirmarSenha são iguais

        Dono dono = donoMapper.toEntity(dto);
        dono.setSenha(passwordEncoder.encode(dono.getSenha()));
        Dono salvo = donoRepository.save(dono);
        return donoMapper.toDTO(salvo);
    }

    /**
     * Verifica se a senha e a confirmação de senha são iguais.
     * Lança IllegalArgumentException se forem diferentes.
     */
    private void validarSenha(DonoRegisterDTO dto) {
        if (!dto.getSenha().equals(dto.getConfirmarSenha())) {
            throw new IllegalArgumentException("As senhas não conferem.");
        }
    }

    /**
     * Realiza login, valida credenciais e gera token JWT.
     */
    public LoginResponseDTO login(DonoLoginDTO dto) {
        Dono dono = donoRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Dono não encontrado"));

        if (!passwordEncoder.matches(dto.getSenha(), dono.getSenha())) {
            throw new BadCredentialsException("Senha inválida");
        }

        String token = jwtUtil.gerarToken(dono.getEmail());
        DonoResponseDTO donoDTO = donoMapper.toDTO(dono);

        return new LoginResponseDTO(token, donoDTO);
    }


    /**
     * Busca dono pelo id, retorna DTO.
     */
    public DonoResponseDTO buscarPorId(Long id) {
        Dono dono = donoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dono não encontrado com id: " + id));
        return donoMapper.toDTO(dono);
    }

    /**
     * Lista todos os donos cadastrados.
     */
    public List<DonoResponseDTO> listarTodos() {
        return donoRepository.findAll()
                .stream()
                .map(donoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza os dados de um dono existente.
     */
    public DonoResponseDTO atualizar(Long id, DonoRegisterDTO dto) {
        Dono donoExistente = donoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dono não encontrado com id: " + id));

        donoExistente.setNome(dto.getNome());
        donoExistente.setEmail(dto.getEmail());

        // Atualiza a senha somente se for enviada no DTO
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            donoExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Dono atualizado = donoRepository.save(donoExistente);
        return donoMapper.toDTO(atualizado);
    }

    /**
     * Remove um dono pelo id.
     */
    public void deletar(Long id) {
        donoRepository.deleteById(id);
    }

    public DonoResponseDTO buscarPorEmail(String email) {
        Dono dono = donoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Dono não encontrado com email: " + email));
        return donoMapper.toDTO(dono);
    }
}






