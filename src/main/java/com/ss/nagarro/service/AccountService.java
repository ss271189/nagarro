package com.ss.nagarro.service;

import com.ss.nagarro.exception.AuthorizationException;
import com.ss.nagarro.exception.InvalidRequestException;
import com.ss.nagarro.model.AccountStatementRequest;
import com.ss.nagarro.model.AccountStatementResposne;

public interface AccountService {

    public AccountStatementResposne getAccountStatement(AccountStatementRequest request, String role) throws InvalidRequestException, AuthorizationException;
}
