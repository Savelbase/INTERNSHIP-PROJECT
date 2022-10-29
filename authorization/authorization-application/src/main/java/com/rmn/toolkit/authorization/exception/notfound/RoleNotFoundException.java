package com.rmn.toolkit.authorization.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoleNotFoundException extends ResponseStatusException {
    public RoleNotFoundException(String roleId) {
        super(HttpStatus.NOT_FOUND, String.format("Role with id='%s' not found", roleId));
    }
}
