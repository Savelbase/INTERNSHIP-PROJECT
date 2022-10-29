package com.rmn.toolkit.mediastorage.command.exception.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FolderCreationException extends ResponseStatusException {
    public FolderCreationException(String path) {
        super(HttpStatus.CONFLICT, String.format("Failed to create folder '%s'", path));
    }
}
