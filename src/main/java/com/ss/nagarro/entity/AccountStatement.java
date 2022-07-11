package com.ss.nagarro.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
public class AccountStatement {
    private String id;
    private String accountType;
    private String accountNumber;
    private LocalDate date;
    private Double amount;
}
