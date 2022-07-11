package com.ss.nagarro.dao;

import com.ss.nagarro.entity.AccountStatement;

import java.util.List;

public interface AccountDao {
    public List<AccountStatement> getAccountStatementById(String accountId);
}
