package com.rmn.toolkit.user.command.security.jwt.service;

import com.rmn.toolkit.user.command.model.type.RoleType;
import com.rmn.toolkit.user.command.security.AuthorityType;
import com.rmn.toolkit.user.command.util.ClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${authentication.token.issuer}")
    private String tokenIssuer;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;
    private final ClientUtil clientUtil;
    private final JwtService jwtService;

    public String generateAccessToken(String clientId, AuthorityType[] authorities) {
        clientUtil.checkClientRights(RoleType.REGISTERED_CLIENT.name());

        Instant currentDateTime = Instant.now();
        Instant expiryDateTime = currentDateTime.plus(accessTokenExpirationSec, ChronoUnit.SECONDS);
        Date issuedAt = new Date(currentDateTime.toEpochMilli());
        Date expiration = new Date(expiryDateTime.toEpochMilli());

        return jwtService.generateAccessToken(tokenIssuer, clientId, authorities, issuedAt, expiration);
    }
}
