package com.ss.nagarro.rest;


import com.ss.nagarro.config.JwtTokenUtil;
import com.ss.nagarro.model.JwtRequest;
import com.ss.nagarro.model.JwtResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService jwtInMemoryUserDetailsService;

    @InjectMocks
    JwtAuthenticationController controller;

    @Mock
    UserDetails userDetails;


    @Test
    public void testCreateAuthenticationToken(){
        JwtRequest request=JwtRequest.builder().username("admin").password("admin").build();
        Mockito.when(jwtInMemoryUserDetailsService.loadUserByUsername(Mockito.any())).thenReturn(userDetails);
        Mockito.when(userDetails.getUsername()).thenReturn("ADMIN");
        Mockito.when(jwtTokenUtil.generateToken(Mockito.any(),Mockito.any())).thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZSI6IlVTRVIiLCJleHAiOjE2NTc1NzE4NDEsImlhdCI6MTY1NzU3MTU0MX0.kNDx6EP_I_wdhOY0QLWJt97tJxt8BIW-iHn68kR8rlYHe7H0bkzTUANI6N1RqywiITb4AW3OdXfdciY09XunCg");
        ResponseEntity<JwtResponse> token=controller.createAuthenticationToken(request);
        Assertions.assertNotNull(token);
    }

}
