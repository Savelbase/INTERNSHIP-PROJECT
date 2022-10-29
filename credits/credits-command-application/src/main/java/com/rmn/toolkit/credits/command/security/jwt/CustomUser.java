package com.rmn.toolkit.credits.command.security.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class CustomUser extends org.springframework.security.core.userdetails.User {
    public CustomUser(String userId, List<String> authorities) {
        super(userId, "", authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
