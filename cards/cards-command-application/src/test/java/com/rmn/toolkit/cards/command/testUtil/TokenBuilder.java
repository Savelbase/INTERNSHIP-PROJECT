package com.rmn.toolkit.cards.command.testUtil;

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
    @Value("${authentication.token.type}")
    private String tokenType;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;
    @Value("${authentication.token.key}")
    private String tokenKey;

    public String getAuthorizationHeaderValue(String accessToken) {
        return String.format("%s %s", tokenType, accessToken);
    }

    public String createAccessTokenWithClientId(String clientId) {
        Instant currentDateTime = Instant.now();
        Date issuedAt = new Date(currentDateTime.toEpochMilli());

        return createAccessToken(clientId, currentDateTime, issuedAt);
    }

    public String createExpiredAccessTokenWithClientId(String clientId) {
        Instant currentDateTime = Instant.now();
        Instant expiredDateTime = currentDateTime.minus(accessTokenExpirationSec, ChronoUnit.SECONDS);
        Date issuedAt = new Date(expiredDateTime.toEpochMilli());

        return createAccessToken(clientId, expiredDateTime, issuedAt);
    }

    private String createAccessToken(String clientId, Instant currentDateTime, Date issuedAt) {
        Instant expiryDateTime = currentDateTime.plus(accessTokenExpirationSec, ChronoUnit.SECONDS);
        Date expiration = new Date(expiryDateTime.toEpochMilli());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(tokenIssuer)
                .setSubject(clientId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put(ACCESS_TOKEN_AUTHORITIES_CLAIMS_KEY, new String[]{
                "CARD_EDIT",
                "EDIT_CARD_ORDER_STATUS"
        });
        jwtBuilder.addClaims(claims);

        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }
}
