package com.rmn.toolkit.deposits.query.security.jwt;



import com.rmn.toolkit.deposits.query.exception.unauthorized.InvalidTokenException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final JwtUtil jwtUtil;
    @Override
    public CustomUser loadUserByUsername(String token) throws UsernameNotFoundException {
        Claims claims = jwtUtil.getClaimsFromJwtToken(token);
        String userId = claims.getSubject();
        if (!jwtUtil.isAccessToken(token)) throw new InvalidTokenException();
        List<String> authorities = claims.get("authorities", List.class);
        return new CustomUser(userId, authorities);
    }
}
