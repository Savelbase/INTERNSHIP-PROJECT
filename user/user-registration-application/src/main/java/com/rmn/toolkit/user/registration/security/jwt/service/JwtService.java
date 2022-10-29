package com.rmn.toolkit.user.registration.security.jwt.service;

import com.rmn.toolkit.user.registration.security.AuthorityType;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final String AUTHORITIES_TOKEN_CLAIMS_KEY = "authorities";

    @Value("${authentication.token.key}")
    private String tokenKey;

    public String generateAccessToken(String issuer, String clientId, AuthorityType[] authorities,
                                      Date issuedAt, Date expiration) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(clientId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_TOKEN_CLAIMS_KEY, authorities);
        jwtBuilder.addClaims(claims);

        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }
}
