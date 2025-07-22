package com.api.product.utils;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private final List<Map<String, String>> errors;

    public ValidationException(List<Map<String, String>> errors) {
        super("Validation Error");
        this.errors = errors;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }
}