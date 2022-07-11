package com.ss.nagarro.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
public class Statement {
    private LocalDate date;
    private Double amount;
}
