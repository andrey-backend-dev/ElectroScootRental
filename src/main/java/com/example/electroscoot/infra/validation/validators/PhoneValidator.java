package com.example.electroscoot.infra.validation.validators;

import com.example.electroscoot.infra.validation.annotations.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        phone = phone.replaceAll("\\s+", "");

        if (phone.matches("\\d{11}")) {
            return true;
        } else if (phone.matches("\\d\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}")) {
            return true;
        } else {
            return (phone.matches("\\d\\(\\d{3}\\)\\d{7}"));
        }
    }
}
