package com.ss.nagarro.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
public class Statement {
    private LocalDate date;
    private Double amount;
}
