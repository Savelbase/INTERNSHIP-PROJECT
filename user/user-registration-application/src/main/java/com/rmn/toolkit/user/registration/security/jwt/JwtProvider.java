package com.rmn.toolkit.user.registration.security.jwt;

import com.rmn.toolkit.user.registration.security.jwt.user.CurrentUser;
import com.rmn.toolkit.user.registration.security.jwt.user.CurrentUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtProvider {
    private final CurrentUserDetailsService userDetailsService;

    public Authentication getAuthentication(String token) {
        CurrentUser userDetails = userDetailsService.loadUserByUsername(token);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}
