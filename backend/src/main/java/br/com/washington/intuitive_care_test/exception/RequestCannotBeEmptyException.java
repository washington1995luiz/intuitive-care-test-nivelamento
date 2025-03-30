package br.com.washington.intuitive_care_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RequestCannotBeEmptyException extends ResponseStatusException {
    public RequestCannotBeEmptyException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
