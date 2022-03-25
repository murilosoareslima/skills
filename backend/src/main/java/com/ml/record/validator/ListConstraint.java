package com.ml.record.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ListConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListConstraint {
    String message() default "A Lista de informações não foi preenchida e é obrigatória.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
