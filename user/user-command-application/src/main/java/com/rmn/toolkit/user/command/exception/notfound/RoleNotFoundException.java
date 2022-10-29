package com.rmn.toolkit.user.command.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoleNotFoundException extends ResponseStatusException {
    public RoleNotFoundException(String name, boolean isName) {
        super(HttpStatus.NOT_FOUND, String.format("Role with name='%s' not found", name));
    }

    public RoleNotFoundException(String roleId) {
        super(HttpStatus.NOT_FOUND, String.format("Role with id='%s' not found", roleId));
    }
}
