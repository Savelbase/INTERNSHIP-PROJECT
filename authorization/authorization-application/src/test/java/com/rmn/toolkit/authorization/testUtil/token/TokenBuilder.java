package com.rmn.toolkit.authorization.testUtil.token;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenBuilder {
    private static final String ACCESS_TOKEN_AUTHORITIES_CLAIMS_KEY = "authorities";

    @Value("${authentication.token.issuer}")
    private String tokenIssuer;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;
    @Value("${authentication.token.refreshTokenExpirationSec}")
    private Integer refreshTokenExpirationSec;
    @Value("${authentication.token.key}")
    private String tokenKey;

    public String generateTokenWithUserId(TokenType tokenType, String userId) {
        Instant currentDateTime = Instant.now();
        Date issuedAt = new Date(currentDateTime.toEpochMilli());
        return createToken(tokenType, userId, currentDateTime, issuedAt);
    }

    public String generateExpiredTokenWithUserId(TokenType tokenType, String userId) {
        Integer tokenExpirationSec = TokenType.ACCESS.equals(tokenType) ?
                accessTokenExpirationSec : refreshTokenExpirationSec;

        Instant currentDateTime = Instant.now();
        Instant expiredDateTime = currentDateTime.minus(tokenExpirationSec, ChronoUnit.SECONDS);
        Date issuedAt = new Date(expiredDateTime.toEpochMilli());
        return createToken(tokenType, userId, expiredDateTime, issuedAt);
    }

    private String generateAccessToken(String userId, Instant currentDateTime, Date issuedAt) {
        Instant expiryDateTime = currentDateTime.plus(accessTokenExpirationSec, ChronoUnit.SECONDS);
        Date expiration = new Date(expiryDateTime.toEpochMilli());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(tokenIssuer)
                .setSubject(userId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put(ACCESS_TOKEN_AUTHORITIES_CLAIMS_KEY, new String[]{
                "REGISTRATION",
                "BANK_CLIENT_REGISTRATION",
                "AUTHORIZATION",
                "USER_EDIT"
        });
        jwtBuilder.addClaims(claims);
        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }

    private String generateRefreshToken(String userId, Instant currentDateTime, Date issuedAt) {
        Instant expiryDateTime = currentDateTime.plus(refreshTokenExpirationSec, ChronoUnit.SECONDS);
        Date expiration = new Date(expiryDateTime.toEpochMilli());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(tokenIssuer)
                .setSubject(userId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration);

        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }

    private String createToken(TokenType tokenType, String userId, Instant currentDateTime, Date issuedAt) {
        return TokenType.ACCESS.equals(tokenType) ?
                generateAccessToken(userId, currentDateTime, issuedAt) :
                generateRefreshToken(userId, currentDateTime, issuedAt);
    }
}
