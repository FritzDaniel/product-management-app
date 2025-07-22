package com.api.product.utils;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class IndexedValidationException extends RuntimeException {
    private final List<List<Map<String, String>>> errors;

    public IndexedValidationException(List<List<Map<String, String>>> errors) {
        super("Validation Error");
        this.errors = errors;
    }
}