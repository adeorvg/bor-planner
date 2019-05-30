package com.pl.pik.exception;

public class ScheduleOperationException extends Exception {
    private SaveScheduleExceptionCause exceptionCause;

    public ScheduleOperationException(String errorMessage) {
        super(errorMessage);
    }
    public ScheduleOperationException(String errorMessage, SaveScheduleExceptionCause exceptionCause) {
        super(errorMessage);
        this.exceptionCause = exceptionCause;
    }

    public SaveScheduleExceptionCause getExceptionCause() {
        return exceptionCause;
    }
}