package com.rapids.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author David on 17/3/14.
 */
@ControllerAdvice(annotations = { RestController.class})
public class CommonExceptionAdvise {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionAdvise.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleCommonException(Exception ex, HttpServletRequest request) {
        String path = request.getServletPath();
        LOGGER.error("server has error , path : {}", path, ex);
    }


    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleTypeMismatchException(TypeMismatchException ex, HttpServletRequest request) {
        String path = request.getServletPath();
        LOGGER.info("TypeMismatchException error, path: {}, exception: {}", path, ex.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleTypeMismatchException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        String path = request.getServletPath();
        LOGGER.info("HttpMediaTypeNotSupportedException error, path: {}, exception: {}", path, ex.getMessage());
    }



    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleHttpMissingRequestParams(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String query = request.getQueryString();
        LOGGER.error(
                "MissingServletRequestParameterException error, query: {}, message: {}", query, ex.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void handleHttpMethodNotSupport(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.error("http method not error, path : {}, method : {}", request.getServletPath(), request.getMethod());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String path = request.getServletPath();
        LOGGER.info("HttpMessageNotReadableException error, path: {}, exception: {}", path, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMethodArgsException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        LOGGER.error("MethodArgumentNotValidException error, path:{}, {}", request.getServletPath(), ex.getMessage());
        if(LOGGER.isDebugEnabled()) {
            BindingResult result = ex.getBindingResult();
            if(result.hasErrors()) {
                result.getAllErrors().forEach(error -> {
                    String key = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
                    LOGGER.debug("object valid error, keyName : {}, message: {}", key, error.getDefaultMessage());
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    @ExceptionHandler({RestClientResponseException.class})
    @ResponseBody
    public ResponseEntity<String> handleRestClientResponseException(HttpServletRequest request, RestClientResponseException ex) {
        LOGGER.error("RestClientResponseException error, path:{}, {}", request.getServletPath(), ex.getMessage());
        return new ResponseEntity(ex.getResponseBodyAsString(), ex.getResponseHeaders(), HttpStatus.valueOf(ex.getRawStatusCode()));
    }
}
