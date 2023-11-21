package by.javaguru.profiler.api.exception;

import java.time.ZonedDateTime;

public record ErrorResponse(int statusCode, String message, ZonedDateTime timeStamp) {
}

