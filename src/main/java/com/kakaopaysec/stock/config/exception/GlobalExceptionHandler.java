package com.kakaopaysec.stock.config.exception;

import com.kakaopaysec.stock.utils.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handleException(Exception ex) {
        log.error("====Exception====", ex);
        return new ResponseEntity<>(ResponseDto.failure("A Temporary Error Has Occurred. Please Try Again In A Few Minutes."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
