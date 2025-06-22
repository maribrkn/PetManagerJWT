package com.pet.example.cadastropet.controller;

import com.pet.example.cadastropet.dto.DonoDTO.DonoRegisterDTO;
import com.pet.example.cadastropet.dto.DonoDTO.DonoResponseDTO;
import com.pet.example.cadastropet.service.DonoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelo CRUD dos donos (usuários).
 * Endpoints para criar, listar, buscar, atualizar e deletar donos.
 * NOTA: Não inclui autenticação/login, que fica no AuthController.
 */
@RestController
@RequestMapping("/donos")  // Base para gerenciamento dos donos
@RequiredArgsConstructor  // Injeta DonoService via construtor automaticamente
public class DonoController {

    private final DonoService donoService;

    /**
     * Registra um novo dono.
     * POST /donos/register
     * Recebe dados de registro e retorna dados do dono criado.
     */
    @PostMapping("/register")
    public ResponseEntity<DonoResponseDTO> register(@RequestBody @Valid DonoRegisterDTO dto) {
        DonoResponseDTO response = donoService.cadastrar(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Busca um dono pelo ID.
     * GET /donos/{id}
     * Retorna dados do dono.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DonoResponseDTO> buscarPorId(@PathVariable Long id) {
        DonoResponseDTO dono = donoService.buscarPorId(id);
        return ResponseEntity.ok(dono);
    }

    /**
     * Lista todos os donos cadastrados.
     * GET /donos
     * Retorna lista de donos.
     */
    @GetMapping
    public ResponseEntity<List<DonoResponseDTO>> listarTodos() {
        List<DonoResponseDTO> donos = donoService.listarTodos();
        return ResponseEntity.ok(donos);
    }

    /**
     * Atualiza dados de um dono existente pelo ID.
     * PUT /donos/{id}
     * Recebe dados atualizados e retorna dono atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DonoResponseDTO> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid DonoRegisterDTO dto) {
        DonoResponseDTO atualizado = donoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Deleta um dono pelo ID.
     * DELETE /donos/{id}
     * Retorna 204 No Content quando deletado com sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        donoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    public ResponseEntity<DonoResponseDTO> buscarDonoAtual(@AuthenticationPrincipal String email) {
        DonoResponseDTO dono = donoService.buscarPorEmail(email);
        return ResponseEntity.ok(dono);
    }
}



