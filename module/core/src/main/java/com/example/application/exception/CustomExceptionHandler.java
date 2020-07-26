package com.example.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
//implements ProblemHandling, SecurityAdviceTrait
public class CustomExceptionHandler{

    @ExceptionHandler(NotFoundException.class)
    public ResponseException handlerNotFoundException(NotFoundException ex){
        return new ResponseException(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler(ExistException.class)
    public ResponseException handlerExistException(ExistException ex){
        return new ResponseException(HttpStatus.UNAUTHORIZED,ex.getMessage());
    }

//    @ExceptionHandler(AppLogicException.class)
//    public ResponseException handlerCustomException(AppLogicException ex){
//        return new ResponseException(HttpStatus.NO_CONTENT,ex.getMessage());
//    }

    @ExceptionHandler(CustomException.class)
    public ResponseException handlerCustomException(CustomException ex){
        return new ResponseException(HttpStatus.NO_CONTENT,ex.getMessage());
    }

//    @ExceptionHandler
//    public ResponseEntity<Problem> handlerCustomException(AppLogicException ex, NativeWebRequest request){
//        if (ex.getDetail() == null) {
//            ex.setDetail("");
//        }
//        Problem problem = Problem.builder()
//                .withTitle(ex.getTitle())
//                .withStatus(ex.getStatus())
//                .with("message", ex.getDetail())
//                .build();
//        return create(ex, problem, request);
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
