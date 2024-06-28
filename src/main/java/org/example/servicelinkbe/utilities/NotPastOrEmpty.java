package org.example.servicelinkbe.utilities;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotPastOrEmptyValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotPastOrEmpty {
    String message() default "The date must be in the future or present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
