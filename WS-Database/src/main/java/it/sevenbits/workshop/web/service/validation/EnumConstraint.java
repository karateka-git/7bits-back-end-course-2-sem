package it.sevenbits.workshop.web.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EnumValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumConstraint {
    Class<? extends Enum<?>> enumClass();
    String message() default "Value is not valid.";
    Class<?>[]groups() default {};
    Class<? extends Payload>[]payload() default {};
}
