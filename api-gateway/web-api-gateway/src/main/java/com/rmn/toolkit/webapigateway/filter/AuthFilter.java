package com.rmn.toolkit.webapigateway.filter;

import com.rmn.toolkit.webapigateway.exception.unauthorized.ExpiredTokenException;
import com.rmn.toolkit.webapigateway.exception.unauthorized.InvalidTokenException;
import com.rmn.toolkit.webapigateway.exception.unauthorized.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    @Value("${authentication.token.key}")
    private String key;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
               throw new UnauthorizedException();
            }
            String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new InvalidTokenException();
            }
            String token = parts[1];
            try {
                getClaimsFromJwtToken(token);
            } catch (ExpiredJwtException ex) {
                log.error("Expired refresh token");
                throw new ExpiredTokenException();
            } catch (Exception ex) {
                log.error("Invalid refresh token");
                throw new InvalidTokenException();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {}

    public void getClaimsFromJwtToken(String token) {
        Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
