package br.com.openumbrella.infinityv.api.service;

import br.com.openumbrella.infinityv.api.model.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Gera um token JWT com base nas informações do usuário
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Cria o token com emissor, assunto e data de expiração
            String token = JWT.create()
                    .withIssuer("infinityv-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating token", exception);

        }
    }

    // Valida um token JWT e retorna o email do usuário associado
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("infinityv-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return ""; // Retorna uma string vazia em caso de verificação inválida

        }
    }

    // Calcula a data de expiração do token (2 horas a partir do momento atual)
    private Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
