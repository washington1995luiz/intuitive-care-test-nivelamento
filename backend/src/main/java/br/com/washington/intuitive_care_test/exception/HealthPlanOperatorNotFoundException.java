package br.com.washington.intuitive_care_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HealthPlanOperatorNotFoundException extends ResponseStatusException {
    public HealthPlanOperatorNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, "Health plan operator not found for this id: ".concat(id));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
