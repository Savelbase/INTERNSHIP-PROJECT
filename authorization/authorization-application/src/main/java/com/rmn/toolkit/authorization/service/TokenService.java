package com.rmn.toolkit.authorization.service;

import com.rmn.toolkit.authorization.dto.response.success.TokensAndUserIdDto;
import com.rmn.toolkit.authorization.exception.conflict.RefreshTokenWithNoSessionException;
import com.rmn.toolkit.authorization.model.RefreshToken;
import com.rmn.toolkit.authorization.model.Role;
import com.rmn.toolkit.authorization.model.User;
import com.rmn.toolkit.authorization.repository.RefreshTokenRepository;
import com.rmn.toolkit.authorization.security.jwt.JwtService;
import com.rmn.toolkit.authorization.security.jwt.JwtUtil;
import com.rmn.toolkit.authorization.util.RefreshTokenUtil;
import com.rmn.toolkit.authorization.util.ResponseUtil;
import com.rmn.toolkit.authorization.util.RoleUtil;
import com.rmn.toolkit.authorization.util.UserUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private static final int CHECKUP_EXPIRY_TIME_OF_REFRESH_TOKEN_IN_MILLISEC = 3_600_000;

    @Value("${authentication.token.issuer}")
    private String jwtIssuer;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;
    @Value("${authentication.token.refreshTokenExpirationSec}")
    private Long refreshTokenExpirationSec;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;
    private final RoleUtil roleUtil;
    private final RefreshTokenUtil refreshTokenUtil;
    private final ResponseUtil responseUtil;

    @Transactional
    public Pair<String, String> generateAccessAndRefreshTokens(User user) {
        userUtil.checkUserRights(user);

        Instant currentTime = Instant.now();
        Instant accessTokenExpirationDate = currentTime.plus(accessTokenExpirationSec, ChronoUnit.SECONDS);
        Instant refreshTokenExpirationDate = currentTime.plus(refreshTokenExpirationSec, ChronoUnit.SECONDS);

        Role role = roleUtil.findRoleById(user.getRoleId());

        String accessToken = jwtService.generateAccessToken(jwtIssuer, user.getId(),
                new Date(currentTime.toEpochMilli()), new Date(accessTokenExpirationDate.toEpochMilli()), role);
        String refreshToken = jwtService.generateRefreshToken(jwtIssuer, user.getId(),
                new Date(currentTime.toEpochMilli()), new Date(refreshTokenExpirationDate.toEpochMilli()));

        String tokenHash = JwtUtil.getTokenHash(refreshToken);
        RefreshToken newRefreshToken = refreshTokenUtil.createRefreshToken(tokenHash,
                refreshTokenExpirationDate, user.getId());
        refreshTokenRepository.save(newRefreshToken);

        return Pair.of(accessToken, refreshToken);
    }

    @Transactional
    public TokensAndUserIdDto generateAccessAndRefreshTokens(String oldRefreshToken) {
        Claims claims = jwtUtil.validateRefreshTokenAndReturnClaims(oldRefreshToken);
        User user = userUtil.findUserById(claims.getSubject());

        Pair<String, String> tokens = generateAccessAndRefreshTokens(user);
        return responseUtil.createTokensAndUserIdDto(tokens, user.getId());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteRefreshToken(String token) {
        Claims claims = jwtUtil.validateRefreshTokenAndReturnClaims(token);
        userUtil.findUserById(claims.getSubject());

        String hash = JwtUtil.getTokenHash(token);
        if (!refreshTokenRepository.existsByHash(hash)) {
            log.error("There isn't associated session with this refresh token");
            throw new RefreshTokenWithNoSessionException();
        }
        refreshTokenRepository.deleteByHash(hash);
    }

    // once an hour
    @Scheduled(fixedRate = CHECKUP_EXPIRY_TIME_OF_REFRESH_TOKEN_IN_MILLISEC)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteAllByExpiryDateTimeBefore(Instant.now());
        log.info("Expired tokens removed from db");
    }
}
