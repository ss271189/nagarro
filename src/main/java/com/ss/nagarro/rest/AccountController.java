package com.ss.nagarro.rest;


import com.ss.nagarro.exception.AuthorizationException;
import com.ss.nagarro.exception.InvalidRequestException;
import com.ss.nagarro.model.AccountStatementRequest;
import com.ss.nagarro.model.AccountStatementResposne;
import com.ss.nagarro.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@Validated
@Slf4j

public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/v1/account/statement")
    public AccountStatementResposne getAccountStatement(@RequestBody AccountStatementRequest request) throws InvalidRequestException, AuthorizationException {
        log.info(" Request : {}",request);
        List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // Assigning valid User role
        String roleAssigned="USER";
        if(roles.contains("ADMIN")){
           roleAssigned="ADMIN";
        }
        // Fetching the response
        AccountStatementResposne response=accountService.getAccountStatement(request,roleAssigned);
        log.info(" Response : {}",response);
        return response;
    }
}
