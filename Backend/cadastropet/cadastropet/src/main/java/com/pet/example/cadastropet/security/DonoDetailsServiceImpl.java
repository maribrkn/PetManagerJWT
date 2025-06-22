package com.pet.example.cadastropet.security;


import com.pet.example.cadastropet.model.Dono;
import com.pet.example.cadastropet.repository.DonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service  // Marca essa classe como um serviço gerenciado pelo Spring (injeção automática)
@RequiredArgsConstructor  // Cria construtor com todos os atributos final (injeção automática do DonoRepository)
public class DonoDetailsServiceImpl implements UserDetailsService {

    private final DonoRepository donoRepository;  // Repositório para buscar dono (usuário)

    // Método obrigatório para Spring Security carregar dados do usuário pela "username" (aqui usamos email)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o Dono pelo email no banco, se não achar, lança exceção
        Dono dono = donoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Constrói um UserDetails (objeto do Spring Security) com email, senha e perfil (autoridade) "USER"
        return org.springframework.security.core.userdetails.User
                .withUsername(dono.getEmail())
                .password(dono.getSenha())
                .authorities("USER")
                .build();
    }
}

