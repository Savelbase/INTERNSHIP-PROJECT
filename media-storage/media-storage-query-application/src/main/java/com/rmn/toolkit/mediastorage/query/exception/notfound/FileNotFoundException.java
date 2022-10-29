package com.rmn.toolkit.mediastorage.query.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileNotFoundException extends ResponseStatusException {
    public FileNotFoundException(String userId) {
        super(HttpStatus.NOT_FOUND, String.format("File with userId='%s' not found", userId));
    }
}
