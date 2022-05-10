package com.ml.record.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class RecordException extends RuntimeException {
    
    @NonNull
    private HttpStatus httpStatus;

    public RecordException(@Nullable String message, @NonNull HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
