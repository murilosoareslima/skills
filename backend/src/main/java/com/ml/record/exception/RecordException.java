package com.ml.record.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecordException extends Exception {
    
    public RecordException(String message) {
        super(message);
    }
}
