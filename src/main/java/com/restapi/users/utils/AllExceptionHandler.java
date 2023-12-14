package com.restapi.users.utils;

import com.restapi.users.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class AllExceptionHandler {

    @Autowired
    private UserService userService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            if ("email".equals(fieldName)) {
                if (!isValidEmail(errorMessage)) {
                    errors.put("email", "Email must be valid");
                } else if (userService.isEmailRegistered(errorMessage)) {
                    errors.put("email", "Email is already registered");
                } else {
                    errors.put(fieldName, errorMessage);
                }
            } else if ("number".equals(fieldName)) {
                if (errorMessage.length() > 9) {
                    errors.put("number", "Number must not exceed 9 characters");
                } else {
                    errors.put(fieldName, errorMessage);
                }
            } else if (errorMessage.isEmpty()) {
                errors.put(fieldName, "Field is required");
            } else {
                errors.put(fieldName, errorMessage);
            }
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}