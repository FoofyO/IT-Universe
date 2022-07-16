package org.example.validators;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = StringFieldValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringField {
    public int min() default 0;
    public int max() default Integer.MAX_VALUE;
    public String pattern() default "";

    public String message() default "* Wrong field format";
    public String messagePattern() default "* Wrong field's pattern";
    public String messageNotEmpty() default "* Field can't be empty";
    public String messageMinLength() default "* Field's data is too short";
    public String messageMaxLength() default "* Field's data is too long";

    public boolean notEmpty() default false;
    public boolean hasPattern() default false;

    public Class<?>[] groups() default {};
    public Class<?>[] payload() default {};
}
