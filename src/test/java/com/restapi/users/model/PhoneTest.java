package com.restapi.users.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testValidPhone() {
        Phone phone = new Phone();
        phone.setNumber("1234567890");
        phone.setCitycode("123");
        phone.setCountrycode("1");

        Set<ConstraintViolation<Phone>> violations = validator.validate(phone);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidNumber() {
        Phone phone = new Phone();
        phone.setNumber("");

        Set<ConstraintViolation<Phone>> violations = validator.validate(phone);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("number field is empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidCityCode() {
        Phone phone = new Phone();
        phone.setNumber("1234567890");
        phone.setCitycode("");

        Set<ConstraintViolation<Phone>> violations = validator.validate(phone);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("citycode field is empty", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidCountryCode() {
        Phone phone = new Phone();
        phone.setNumber("1234567890");
        phone.setCitycode("123");
        phone.setCountrycode("");

        Set<ConstraintViolation<Phone>> violations = validator.validate(phone);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("countrycode field is empty", violations.iterator().next().getMessage());
    }
}