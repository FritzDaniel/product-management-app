package com.api.product.auth;

import com.api.product.auth.dto.AuthRequest;
import com.api.product.auth.dto.SignUpRequest;
import com.api.product.auth.services.JwtTokenService;
import com.api.product.auth.services.UserService;
import com.api.product.entity.UserInfo;
import com.api.product.repository.UserInfoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private UserService authService;

    @Mock
    private UserInfoRepo userInfoRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("newuser");
        request.setPassword("pass");

        when(userInfoRepo.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("hashed-pass");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(mockAuth.getAuthorities()).thenReturn(List.of());
        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);
        when(jwtService.createToken(eq("newuser"), any())).thenReturn("jwt-token");

        String token = authService.register(request);

        assertEquals("jwt-token", token);
        verify(userInfoRepo, times(1)).save(any());
    }

    @Test
    void testRegisterUsernameExists() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("existing");

        when(userInfoRepo.findByUsername("existing")).thenReturn(Optional.of(new UserInfo()));

        Exception ex = assertThrows(RuntimeException.class, () -> authService.register(request));
        assertEquals("Username already exists", ex.getMessage());
    }

    @Test
    void testAuthenticateSuccess() {
        AuthRequest request = new AuthRequest();
        request.setUsername("valid");
        request.setPassword("pass");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(mockAuth.getAuthorities()).thenReturn(List.of());
        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);
        when(jwtService.createToken(eq("valid"), any())).thenReturn("mocked-token");

        String token = authService.authenticate(request);

        assertEquals("mocked-token", token);
    }

    @Test
    void testAuthenticateFailure() {
        AuthRequest request = new AuthRequest();
        request.setUsername("invalid");
        request.setPassword("wrong");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        assertThrows(BadCredentialsException.class, () -> {
            authService.authenticate(request);
        });
    }
}
