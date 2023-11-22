package com.example.budgetmanageservice.user.validation.validator;

import com.example.budgetmanageservice.user.validation.annotation.CommonPasswordValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CommonPasswordValidator implements ConstraintValidator<CommonPasswordValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }

    @Override
    public void initialize(CommonPasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


}
