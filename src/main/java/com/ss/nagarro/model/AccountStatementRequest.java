package com.ss.nagarro.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class AccountStatementRequest {


    private String accountId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer fromAmount;
    private Integer toAmount;
}
