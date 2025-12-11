package com.bestpick.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bestpick.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@NoArgsConstructor
public class JWTService {

    private SecretKey secretKey;

    @Value("${bestpick.secretkey}")
    String keyString;

    @PostConstruct
    public void init() {
        try {
            byte[] keyBytes = keyString.getBytes("UTF-8");
            secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar la clave secreta", e);
        }
    }

    public Boolean isValid(String token) {

        if (token == null || token.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token must not be null");
        }

        try {
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            log.warn("Token expirado: " + token);
            return false;
        } catch (Exception e) {
            log.error("Error al validar el token JWT", e);
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date());
    }

    private Date extractExpirationTime(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el token JWT", e);
        }
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .add("X-User-Id", user.getId())
                .add("X-application", "bestPick")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30 * 5))
                .and()
                .signWith(secretKey)
                .compact();
    }

}
