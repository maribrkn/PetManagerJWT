package com.pet.example.cadastropet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration  // Informa ao Spring que esta classe fornece configurações (beans)
@RequiredArgsConstructor  // Cria construtor com os campos finais (como o jwtFilter)
public class SecurityConfig {

    private final JwtFilter jwtFilter; // Filtro personalizado para validar tokens JWT

    @Bean  // Define o bean que configura a segurança HTTP da aplicação
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa proteção CSRF (necessária apenas em apps com sessões e formulários, não APIs REST)
                .headers(headers -> headers.disable()) // Desativa alguns headers padrão (opcional)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Ativa CORS com a configuração personalizada
                .authorizeHttpRequests(auth -> auth
                        // Libera acesso sem autenticação para endpoints públicos
                        .requestMatchers("/auth/login", "/donos/register", "/h2-console/**", "/").permitAll()
                        // Todos os outros endpoints exigem autenticação com token
                        .anyRequest().authenticated()
                )
                // Define que não será criada nenhuma sessão (pois usamos autenticação via token)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Adiciona o filtro JWT antes do filtro padrão de autenticação por login/senha
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Retorna o objeto configurado de segurança
    }

    // Bean que define as regras de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Define quais origens podem acessar a API (React roda localmente em localhost:3000)
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));

        // Quais métodos HTTP são permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Quais headers podem ser enviados pelo front
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        // Permite ou não envio de credenciais (como cookies, se for o caso)
        configuration.setAllowCredentials(true);

        // Aplica essa configuração para todas as rotas da API
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source; // Retorna o bean com a configuração de CORS
    }

    // Bean para codificação segura de senhas com BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



