package com.restapi.users.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.restapi.users.service.UserService;
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
            PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();


            if ("email".equals(fieldName)) {
                if (!isValidEmail(errorMessage)) {
                    errors.put("error02", "Formato incorrecto de correo, porfavor ingresar otra vez");
                } else if (userService.isEmailRegistered(errorMessage)) {
                    errors.put("error01", "El correo ya esta registrado en el Banco BCI, por favor ingrese uno diferente");
                } else {
                    errors.put(fieldName, errorMessage);
                }
            }  else if ("number".equals(fieldName)) {
                if (!phoneNumberValidator.isValidPhoneNumber(errorMessage)) {
                    errors.put("number", "El número de teléfono no es válido");
                } else if (errorMessage.length() > 9) {
                    errors.put("number", "El número excede la cantidad de digitos, por favor ingrese otra vez ");
                } else {
                    errors.put(fieldName, errorMessage);
                }
            } else if (errorMessage.isEmpty()) {
                errors.put(fieldName, "Este campo es requerido, porfavor intente otra vez");
            } else {
                errors.put(fieldName, "Este campo es requerido, porfavor intente otra vez");
            }
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }


    }

    private boolean isValidEmail (String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}