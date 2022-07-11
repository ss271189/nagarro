package com.ss.nagarro.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
