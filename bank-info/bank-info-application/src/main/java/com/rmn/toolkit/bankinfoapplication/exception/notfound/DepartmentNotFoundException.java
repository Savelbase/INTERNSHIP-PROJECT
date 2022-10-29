package com.rmn.toolkit.bankinfoapplication.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepartmentNotFoundException extends ResponseStatusException {

    public DepartmentNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Department with id='%s' not found", id));
    }
}

