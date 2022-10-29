package com.rmn.toolkit.user.registration.security.jwt.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

public class CurrentUser extends User {

    public CurrentUser(String userId, List<String> authorities) {
        super(userId, "", authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
