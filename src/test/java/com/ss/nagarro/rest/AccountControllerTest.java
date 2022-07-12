package com.ss.nagarro.rest;


import com.ss.nagarro.exception.AuthorizationException;
import com.ss.nagarro.exception.InvalidRequestException;
import com.ss.nagarro.model.AccountStatementRequest;
import com.ss.nagarro.model.AccountStatementResposne;
import com.ss.nagarro.service.AccountService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController controller;

    @Mock
    Authentication authentication;

    @Test
    public void testGetAccountStatementForAdmin() throws AuthorizationException, InvalidRequestException {
        AccountStatementRequest request = AccountStatementRequest.builder()
                .accountId("1")
                .build();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(authority);
        Mockito.when(authentication.getAuthorities()).thenReturn((List) updatedAuthorities);
        Mockito.when(accountService.getAccountStatement(Mockito.any(), Mockito.anyString())).thenReturn(AccountStatementResposne.builder().build());
        AccountStatementResposne resposne = controller.getAccountStatement(request);
        Assertions.assertNotNull(resposne);
    }


    @Test
    public void testGetAccountStatementForUser() throws AuthorizationException, InvalidRequestException {
        AccountStatementRequest request = AccountStatementRequest.builder()
                .accountId("1")
                .build();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(authority);
        Mockito.when(authentication.getAuthorities()).thenReturn((List) updatedAuthorities);
        Mockito.when(accountService.getAccountStatement(Mockito.any(), Mockito.anyString())).thenReturn(AccountStatementResposne.builder().build());
        AccountStatementResposne resposne = controller.getAccountStatement(request);
        Assertions.assertNotNull(resposne);
    }
}
