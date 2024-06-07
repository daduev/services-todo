package org.home.dev.config;

import org.home.dev.dto.exception.ErrorResponseDto;
import org.home.dev.exception.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(
            BadRequestException ex, WebRequest request) {
        return new ResponseEntity<ErrorResponseDto>(
                new ErrorResponseDto(ex.getStatusCode(), ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        final FieldError error = ex.getBindingResult().getFieldErrors()
                .stream().findFirst().orElseThrow(() -> new BadRequestException("Something goes wrong!"));
        return new ResponseEntity<ErrorResponseDto>(
                new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
