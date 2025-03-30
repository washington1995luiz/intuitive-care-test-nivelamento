package br.com.washington.intuitive_care_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadFormattedUUIDException extends ResponseStatusException {
    public BadFormattedUUIDException(String id) {
        super(HttpStatus.BAD_REQUEST, "Bad formatted UUID: ".concat(id));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
