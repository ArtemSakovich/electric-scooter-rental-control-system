package com.sakovich.scooterrental.api.handler;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.payload.OperationCancelledExceptionPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class OperationCancelledExceptionHandler {

    @ExceptionHandler(value = {OperationCancelledException.class})
    public ResponseEntity<Object> handle(OperationCancelledException e) {
        OperationCancelledExceptionPayload exception = new OperationCancelledExceptionPayload(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
