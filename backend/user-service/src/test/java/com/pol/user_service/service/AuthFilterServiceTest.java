package com.pol.user_service.service;

import com.pol.user_service.auth.model.CustomUserDetails;
import com.pol.user_service.auth.service.AuthFilterService;
import com.pol.user_service.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthFilterServiceTest {

    private AuthFilterService authFilterService;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authFilterService = new AuthFilterService(jwtService, userDetailsService);
    }

    private void invokeDoFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws Exception {
        var method = AuthFilterService.class.getDeclaredMethod(
                "doFilterInternal", HttpServletRequest.class, HttpServletResponse.class, FilterChain.class
        );
        method.setAccessible(true);
        method.invoke(authFilterService, request, response, filterChain);
    }

    @Test
    @DisplayName("Should not authenticate when Authorization header does not start with Bearer")
    void givenInvalidAuthorizationHeader_whenFilterInvoked_thenDoNothing() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

        invokeDoFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractUsername(any());
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Should authenticate valid JWT and set SecurityContext")
    void givenValidJwt_whenFilterInvoked_thenAuthenticateUser() throws Exception {
        String jwt = "valid.jwt.token";
        String username = "testUser";
        CustomUserDetails userDetails = mock(CustomUserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(jwt, userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(null);

        invokeDoFilterInternal(request, response, filterChain);

        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(filterChain).doFilter(request, response);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(userDetails);

        verify(userDetails).getAuthorities();
    }

    @Test
    @DisplayName("Should not authenticate when JWT is invalid")
    void givenInvalidJwt_whenFilterInvoked_thenDoNothing() throws Exception {
        String jwt = "invalid.jwt.token";
        String username = "testUser";
        CustomUserDetails userDetails = mock(CustomUserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(jwt, userDetails)).thenReturn(false);

        invokeDoFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Should not authenticate when SecurityContext already has authentication")
    void givenAlreadyAuthenticated_whenFilterInvoked_thenDoNothing() throws Exception {
        String jwt = "valid.jwt.token";
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);

        invokeDoFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractUsername(any());
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(authenticationToken);
    }
}
