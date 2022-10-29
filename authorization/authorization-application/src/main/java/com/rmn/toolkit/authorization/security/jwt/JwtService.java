package com.rmn.toolkit.authorization.security.jwt;

import com.rmn.toolkit.authorization.model.Role;
import com.rmn.toolkit.authorization.security.AuthorityType;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    private static final String AUTHORITIES_TOKEN_CLAIMS_KEY = "authorities";
    private static final String ROLE_TOKEN_CLAIMS_KEY = "role";

    @Value("${authentication.token.key}")
    private String tokenKey;

    public String generateAccessToken(String issuer, String userId, Date issuedAt, Date expiration, Role role) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_TOKEN_CLAIMS_KEY, role.getAuthorities());
        claims.put(ROLE_TOKEN_CLAIMS_KEY, role.getName());
        jwtBuilder.addClaims(claims);

        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }

    public String generateRefreshToken(String issuer, String userId, Date issuedAt, Date expiration) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userId)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration);

        Map<String, Object> claims = new HashMap<>();
        jwtBuilder.addClaims(claims);

        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }
}
