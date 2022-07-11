package com.ss.nagarro.model;

import lombok.*;

import java.time.LocalDate;
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

