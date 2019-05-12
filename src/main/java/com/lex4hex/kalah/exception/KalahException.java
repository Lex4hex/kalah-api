package com.lex4hex.kalah.exception;

import lombok.Getter;

public class KalahException extends RuntimeException {
    @Getter
    private ErrorType errorType;

    public KalahException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public KalahException() {
    }
}
