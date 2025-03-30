package br.com.washington.intuitive_care_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FinancialStatementsNotFoundException extends ResponseStatusException {
    public FinancialStatementsNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, "Financial statements with id " + id + " not found");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
