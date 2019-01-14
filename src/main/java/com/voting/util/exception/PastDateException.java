package com.voting.util.exception;

import org.springframework.http.HttpStatus;

public class PastDateException extends ApplicationException {
    public static final String EXCEPTION_PAST_DATE = "exception.voting.beforeLate";

    public PastDateException(String message) {
        super(EXCEPTION_PAST_DATE, HttpStatus.NOT_ACCEPTABLE);
    }
}

