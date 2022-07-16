package org.example.validators;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class StringFieldValidator implements ConstraintValidator<StringField, String> {

    private Integer min;
    private Integer max;
    private String pattern;
    private Boolean notEmpty;
    private Boolean hasPattern;
    private String messagePattern;
    private String messageNotEmpty;
    private String messageMinLength;
    private String messageMaxLength;

    @Override
    public void initialize(StringField field) {
        min = field.min();
        max = field.max();
        pattern = field.pattern();
        notEmpty = field.notEmpty();
        hasPattern = field.hasPattern();
        messagePattern = field.messagePattern();
        messageNotEmpty = field.messageNotEmpty();
        messageMinLength = field.messageMinLength();
        messageMaxLength = field.messageMaxLength();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if(value == null) return true;
        if (notEmpty && value.isEmpty()) {
            context.buildConstraintViolationWithTemplate(messageNotEmpty).addConstraintViolation();
            return false;
        }
        if (min > 0 && value.length() < min) {
            context.buildConstraintViolationWithTemplate(messageMinLength).addConstraintViolation();
            return false;
        }
        if (max < Integer.MAX_VALUE && value.length() > max) {
            context.buildConstraintViolationWithTemplate(messageMaxLength).addConstraintViolation();
            return false;
        }
        if(hasPattern) {
            if(!value.matches(this.pattern)) {
                context.buildConstraintViolationWithTemplate(messagePattern).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}