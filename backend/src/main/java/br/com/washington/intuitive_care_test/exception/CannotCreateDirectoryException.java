package br.com.washington.intuitive_care_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CannotCreateDirectoryException extends ResponseStatusException {
    public CannotCreateDirectoryException(String message) {
        super(HttpStatus.CONFLICT, "Cannot create directory because already exists.\nmessage-exception: " + message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
