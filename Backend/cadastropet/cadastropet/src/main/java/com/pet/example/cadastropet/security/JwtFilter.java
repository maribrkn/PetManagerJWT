package com.pet.example.cadastropet.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Componente gerenciado pelo Spring (servirá como filtro na cadeia de filtros)
@RequiredArgsConstructor  // Injeta automaticamente JwtUtil e UserDetailsService
public class JwtFilter extends OncePerRequestFilter {  // Executa uma vez por requisição

    private final JwtUtil jwtUtil;  // Classe utilitária para manipular JWT
    private final UserDetailsService userDetailsService;  // Serviço para carregar usuário do banco

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Pega o header "Authorization" da requisição HTTP
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Verifica se o header existe e começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extrai só o token, sem o "Bearer "
            jwt = authHeader.substring(7);

            // Valida o token JWT
            if (jwtUtil.validarToken(jwt)) {
                // Extrai o username (email) do token
                username = jwtUtil.extrairUsername(jwt);
            }
        }

        // Se username existe e não há autenticação no contexto do Spring Security ainda
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Busca os dados do usuário no banco
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Cria um token de autenticação para o Spring Security
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // Configura detalhes da requisição para o token
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Define o token no contexto de segurança, permitindo que o usuário esteja autenticado na requisição
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Continua a cadeia de filtros (passa para o próximo filtro ou para o controller)
        filterChain.doFilter(request, response);
    }
}

