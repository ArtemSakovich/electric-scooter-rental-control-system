package com.sakovich.scooterrental.api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class OperationCancelledExceptionPayload {

    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
}
