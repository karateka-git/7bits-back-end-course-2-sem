package it.sevenbits.workshop.web.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * validator for enum
 */
public class EnumValidator implements
        ConstraintValidator<EnumConstraint, String> {

    private EnumConstraint annotation;
    @Override
    public void initialize(final EnumConstraint constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (value == null) {
            return true;
        }
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue:enumValues) {
                if (value.equals(enumValue.toString())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
