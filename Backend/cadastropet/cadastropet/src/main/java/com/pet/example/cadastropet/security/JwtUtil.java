package com.pet.example.cadastropet.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component  // Componente Spring, pode ser injetado em outros lugares
public class JwtUtil {

    // Gera uma chave secreta automaticamente para assinar o JWT usando HS256 (simetrico)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tempo de expiração do token (1 hora em milissegundos)
    private final long expiration = 3600000;

    // Gera um token JWT com o username como "subject"
    public String gerarToken(String username){
        return Jwts.builder()
                .setSubject(username)  // Identificador principal do token (username/email)
                .setIssuedAt(new Date())  // Data de criação do token
                .setExpiration(new Date(System.currentTimeMillis() + expiration))  // Data de expiração
                .signWith(key)  // Assina o token com a chave secreta
                .compact();  // Compacta para String
    }

    // Extrai o username (subject) de dentro do token JWT
    public String extrairUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)  // Usa a mesma chave para verificar assinatura
                .build()
                .parseClaimsJws(token)  // Faz o parse e valida o token
                .getBody()
                .getSubject();  // Pega o campo "sub"
    }

    // Valida se o token é válido (assinatura correta e não expirado)
    public boolean validarToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;  // Se não lançar exceção, token válido
        } catch (JwtException | IllegalArgumentException e){
            return false;  // Token inválido ou mal formado
        }
    }
}

