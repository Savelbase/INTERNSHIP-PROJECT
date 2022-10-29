package com.rmn.toolkit.user.registration.exception.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientAlreadyRegisteredException extends ResponseStatusException {
    public ClientAlreadyRegisteredException(String clientId) {
        super(HttpStatus.CONFLICT, String.format("Client with mobile phone='%s' already is registered", clientId));
    }

    public ClientAlreadyRegisteredException(String mobilePhone, boolean isMobilePhone) {
        super(HttpStatus.CONFLICT, String.format("Client with mobile phone='%s' already is registered", mobilePhone));
    }
}
