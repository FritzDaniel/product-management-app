package com.api.product.auth.services;

import com.api.product.auth.dto.AuthRequest;
import com.api.product.auth.dto.SignUpRequest;
import com.api.product.entity.UserInfo;
import com.api.product.repository.UserInfoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class UserService {

    final UserInfoRepo userInfoRepo;
    final PasswordEncoder passwordEncoder;
    final JwtTokenService jwtService;
    final AuthenticationManager authenticationManager;

    public UserService(UserInfoRepo userInfoRepo, PasswordEncoder passwordEncoder, JwtTokenService jwtService, AuthenticationManager authenticationManager) {
        this.userInfoRepo = userInfoRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(AuthRequest loginRequest) {
        try {
            // Check if input is an email or username
            String identifier = loginRequest.getUsername();

            // Authenticate with the determined identifier
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(identifier, loginRequest.getPassword())
            );

            if (auth.isAuthenticated()) {
                Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
                return jwtService.createToken(identifier, authorities);
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public String register(SignUpRequest request) {
        if (userInfoRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        UserInfo user = new UserInfo();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");

        userInfoRepo.save(user);


        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (auth.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            return jwtService.createToken(request.getUsername(), authorities);
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public UserInfo profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userInfoRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }

}
