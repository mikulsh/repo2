package com.operations.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeException.EntityNotFoundException.class)
    public final ResponseEntity handleNotFoundException(Exception ex, WebRequest request){
        Response response = Response.notFound();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }
}
