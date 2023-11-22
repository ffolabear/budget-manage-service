package com.example.budgetmanageservice.user.validation.validator;

import com.example.budgetmanageservice.user.validation.annotation.CommonPasswordValid;
import com.example.budgetmanageservice.user.validation.utils.CommonPasswords;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CommonPasswordValidator implements ConstraintValidator<CommonPasswordValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !CommonPasswords.isCommonPassword(value);
    }

    @Override
    public void initialize(CommonPasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


}
