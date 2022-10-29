package com.rmn.toolkit.authorization.security.jwt;

import com.rmn.toolkit.authorization.exception.unauthorized.ExpiredRefreshTokenException;
import com.rmn.toolkit.authorization.exception.unauthorized.InvalidRefreshTokenException;
import com.rmn.toolkit.authorization.exception.conflict.RefreshTokenWithNoSessionException;
import com.rmn.toolkit.authorization.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {
    private static final String AUTHORITIES_TOKEN_CLAIMS_KEY = "authorities";

    @Value("${authentication.token.key}")
    private String tokenKey;
    private final RefreshTokenRepository refreshTokenRepository;

    public static String getTokenHash(String token) {
        return DigestUtils.sha3_256Hex(token.getBytes(StandardCharsets.UTF_8));
    }

    @Transactional(readOnly = true)
    public Claims validateRefreshTokenAndReturnClaims(String token) {
        Claims claims = getClaimsFromJwt(token);
        if (!isRefreshToken(token)) {
            log.error("Invalid refresh token");
            throw new InvalidRefreshTokenException();
        }
        if (!isRefreshTokenExist(token)) {
            log.error("There isn't associated session with this refresh token");
            throw new RefreshTokenWithNoSessionException();
        }
        return claims;
    }

    public Claims getClaimsFromJwt(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            log.error("Expired refresh token");
            throw new ExpiredRefreshTokenException();
        } catch (MalformedJwtException | SignatureException ex) {
            log.error("Invalid refresh token");
            throw new InvalidRefreshTokenException();
        }
    }

    public boolean isRefreshToken(String token) {
        return !getClaimsFromJwt(token).containsKey(AUTHORITIES_TOKEN_CLAIMS_KEY);
    }

    public boolean isRefreshTokenExist(String token) {
        String tokenHash = JwtUtil.getTokenHash(token);
        return refreshTokenRepository.existsByHash(tokenHash);
    }
}