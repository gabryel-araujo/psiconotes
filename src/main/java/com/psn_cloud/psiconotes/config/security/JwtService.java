package com.psn_cloud.psiconotes.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.psn_cloud.psiconotes.domain.OrganizacaoUsuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    // Em producao, isso deveria vir de variaveis de ambiente
    @Value("${api.security.token.secret:my-secret-key-psiconotes}")
    private String secret;

    public String generateToken(OrganizacaoUsuario usuario, String tenantId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("psiconotes-api")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId().toString())
                    .withClaim("role", usuario.getRole().name())
                    .withClaim("tenant", tenantId) // Injeta o tenant no token para prevenir BOLA
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // Algoritmo hardcoded impede ataque alg:none
            return JWT.require(algorithm)
                    .withIssuer("psiconotes-api")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
