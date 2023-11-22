package com.example.budgetmanageservice.user.validation.validator;

import com.example.budgetmanageservice.user.dto.UserRegisterRequestDto;
import com.example.budgetmanageservice.user.validation.annotation.PasswordInfoSameValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordInfoSameValidator implements ConstraintValidator<PasswordInfoSameValid, UserRegisterRequestDto> {

    @Override
    public void initialize(PasswordInfoSameValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserRegisterRequestDto value, ConstraintValidatorContext context) {
        String password = value.getPassword();
        String userName = value.getUserName();

        return !password.contains(userName);
    }
}
