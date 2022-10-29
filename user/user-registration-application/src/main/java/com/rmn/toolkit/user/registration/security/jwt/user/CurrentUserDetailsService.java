package com.rmn.toolkit.user.registration.security.jwt.user;

import com.rmn.toolkit.user.registration.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrentUserDetailsService implements UserDetailsService {
    private static final String AUTHORITIES_TOKEN_CLAIMS_KEY = "authorities";

    private final JwtUtil jwtUtil;

    @Override
    public CurrentUser loadUserByUsername(String token) throws UsernameNotFoundException {
        Claims claims = jwtUtil.getClaimsFromJwt(token);
        return new CurrentUser(claims.getSubject(), claims.get(AUTHORITIES_TOKEN_CLAIMS_KEY, List.class));
    }
}
