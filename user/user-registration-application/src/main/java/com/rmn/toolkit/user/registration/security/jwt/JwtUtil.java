package com.rmn.toolkit.user.registration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final String AUTHORITIES_TOKEN_CLAIMS_KEY = "authorities";
    private static final String JWT_CLAIMS_CACHE_NAME = "jwtClaims";

    @Value("${authentication.token.type}")
    private String tokenType;
    @Value("${authentication.token.key}")
    private String tokenKey;

    @Cacheable(JWT_CLAIMS_CACHE_NAME)
    public Claims getClaimsFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(tokenKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isAccessToken(String token) {
        return getClaimsFromJwt(token).containsKey(AUTHORITIES_TOKEN_CLAIMS_KEY);
    }

    public String getTokenFromHeader(String header) {
        if (!StringUtils.isBlank(header) && header.startsWith(tokenType)) {
            return header.substring(tokenType.length() + 1);
        }
        return null;
    }
}
