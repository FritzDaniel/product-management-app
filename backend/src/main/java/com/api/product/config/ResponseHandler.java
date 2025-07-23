package com.api.product.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(String message, Integer httpStatus, Object responseObject) {
        Map<String, Object> response = new HashMap<>();

        response.put("data", responseObject);
        response.put("message", message);
        response.put("httpStatus", httpStatus);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(httpStatus));
    }
}

