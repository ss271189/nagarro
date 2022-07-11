package com.ss.nagarro.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Data
public class AccountStatementResposne {
    private String accountId;
    private String accountNumber;
    private List<Statement> statements;


}

