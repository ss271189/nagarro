package com.ss.nagarro.model;

import java.time.LocalDate;

public class CustomExceptionResponse {
    private LocalDate date;
    private String message;
    private String details;


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "CustomExceptionResponse{" +
                "date=" + date +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }

    public CustomExceptionResponse(LocalDate date, String message, String details) {
        this.date = date;
        this.message = message;
        this.details = details;
    }

}
