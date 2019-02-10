package com.example.demo.controller.advice;

import com.example.demo.dto.AdviceDTO;
import com.example.demo.exceptions.NoSuchAuthorException;
import com.example.demo.exceptions.NoSuchBookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(NoSuchAuthorException.class)
    public ResponseEntity<AdviceDTO> noSuchAuthorExceptionAdvice() {
        return new ResponseEntity<>(new AdviceDTO("No specified author found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchBookException.class)
    public ResponseEntity<AdviceDTO> noSuchBookExceptionAdvice() {
        return new ResponseEntity<>(new AdviceDTO("No specified book found."), HttpStatus.NOT_FOUND);
    }
}
