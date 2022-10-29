package com.rmn.toolkit.cards.command.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class JwtUtil {
    @Value("${authentication.token.key}")
    private String key;
    @Value("${authentication.token.type}")
    private String tokenType;

    @Cacheable("jwtClaims")
    public Claims getClaimsFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isAccessToken(String token) {
        return getClaimsFromJwtToken(token).containsKey("authorities");
    }

    public String getTokenFromHeader(String header) {
        if (StringUtils.hasText(header) && header.startsWith(tokenType)) {
            return header.substring(tokenType.length() + 1);
        }
        return null;
    }
}
