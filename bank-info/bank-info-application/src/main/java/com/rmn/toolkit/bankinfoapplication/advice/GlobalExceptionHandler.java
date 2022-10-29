package com.rmn.toolkit.bankinfoapplication.advice;

import com.rmn.toolkit.bankinfoapplication.dto.response.error.DepartmentNotFoundResponse;
import com.rmn.toolkit.bankinfoapplication.exception.notfound.DepartmentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<DepartmentNotFoundResponse> handleClientStatusBlockedException(DepartmentNotFoundException ex) {
        DepartmentNotFoundResponse response = new DepartmentNotFoundResponse(ex.getReason());
        return new ResponseEntity<>(response , HttpStatus.NOT_FOUND);
    }
}
