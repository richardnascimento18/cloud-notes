package com.br.cloudnotes.adapters.in.web;

import com.br.cloudnotes.adapters.in.web.dto.ApiResponseDto;
import com.br.cloudnotes.adapters.in.web.dto.ErrorBodyDto;
import com.br.cloudnotes.core.domain.exceptions.PageNotFoundException;
import com.br.cloudnotes.core.domain.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<ErrorBodyDto>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        ErrorBodyDto errorBodyDto = new ErrorBodyDto("#400", errorMessage);

        ApiResponseDto<ErrorBodyDto> response = new ApiResponseDto<>(
                HttpStatus.BAD_REQUEST.value(),
                errorBodyDto,
                Map.of()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ApiResponseDto<Object> response = new ApiResponseDto<>(
                HttpStatus.CONFLICT.value(),
                Map.of("error", ex.getMessage(), "code", "#409"),
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handlePageNotFound(PageNotFoundException ex) {
        ApiResponseDto<Object> response = new ApiResponseDto<>(
                HttpStatus.NOT_FOUND.value(),
                Map.of("error", ex.getMessage(), "code", "#404"),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Object>> handleGeneric(Exception ex) {
        ApiResponseDto<Object> response = new ApiResponseDto<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Map.of("error", "Unexpected error", "details", ex.getMessage()),
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
