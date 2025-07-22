package com.api.product.auth.controllers;

import com.api.product.auth.dto.AuthRequest;
import com.api.product.auth.dto.SignUpRequest;
import com.api.product.auth.services.UserService;
import com.app.filoom.config.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(@RequestBody SignUpRequest registerRequest){

        try {
            String token = userService.register(registerRequest);

            Map<String, String> dataToken = new HashMap<>();
            dataToken.put("token", token);

            return ResponseHandler.responseBuilder("Success login.", HttpStatus.OK.value(), dataToken);
        } catch (BadCredentialsException | IllegalArgumentException e) {
            return ResponseHandler.responseBuilder("An unexpected error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),null);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody AuthRequest loginRequest){

        try {
            String token = userService.authenticate(loginRequest);

            Map<String, String> dataToken = new HashMap<>();
            dataToken.put("token", token);

            return ResponseHandler.responseBuilder("Success login.", HttpStatus.OK.value(), dataToken);
        } catch (BadCredentialsException | IllegalArgumentException e) {
            return ResponseHandler.responseBuilder("An unexpected error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),null);
        }
    }

    @GetMapping("/auth/profile")
    public ResponseEntity<Object> myProfile(){
        try {
            return ResponseHandler.responseBuilder("Get profile.", HttpStatus.OK.value(), userService.profile());
        } catch (IllegalArgumentException e) {
            return ResponseHandler.responseBuilder("An unexpected error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),null);
        }
    }
}
