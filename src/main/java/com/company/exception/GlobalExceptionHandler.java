package com.company.exception;


import com.company.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIResponse<Object>> handleResourceNotFound(ResourceNotFound ex) {
        APIResponse<Object> apiResponse = new APIResponse<>(
                HttpStatus.NOT_FOUND.value(), ex.getMessage(), null
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Object>> handleGlobalException(Exception ex) {
        APIResponse<Object> apiResponse = new APIResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFound(ResourceNotFound ex)
    {
        Map<String,Object> response=new HashMap<>();
        response.put("timeStamp",LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error","Not Found");
        response.put("message",ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }*/

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGlobalException(Exception ex)
    {
        Map<String,Object> response=new HashMap<>();
        response.put("timeStamp",LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error","Internal Server Error");
        response.put("message",ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }*/


}
