package com.ss.nagarro.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class AccountStatementRequest {

    @NonNull
    private String accountId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer fromAmount;
    private Integer toAmount;
}
