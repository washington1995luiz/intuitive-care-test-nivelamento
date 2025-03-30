package br.com.washington.intuitive_care_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Top10OperatorsNotFoundException extends ResponseStatusException {
    public Top10OperatorsNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Top 10 operators not found");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
