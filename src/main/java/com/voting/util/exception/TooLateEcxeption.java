package com.voting.util.exception;

import org.springframework.http.HttpStatus;

public class TooLateEcxeption  extends ApplicationException {
    public static final String EXCEPTION_TODATE_LATE = "exception.voting.todatayLate";

    public TooLateEcxeption(String message) {
            super(EXCEPTION_TODATE_LATE, HttpStatus.NOT_ACCEPTABLE);
        }

}
