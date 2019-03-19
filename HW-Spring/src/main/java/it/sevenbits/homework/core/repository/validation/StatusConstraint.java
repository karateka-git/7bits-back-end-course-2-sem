package it.sevenbits.homework.core.repository.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {StatusValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusConstraint {
    Class<? extends Enum<?>> enumClass();
    String message() default "Value status is not valid.";
    Class<?>[]groups() default {};
    Class<? extends Payload>[]payload() default {};
}
