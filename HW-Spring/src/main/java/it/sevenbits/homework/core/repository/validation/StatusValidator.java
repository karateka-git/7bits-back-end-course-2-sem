package it.sevenbits.homework.core.repository.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusValidator implements
        ConstraintValidator<StatusConstraint, String> {

    private StatusConstraint annotation;
    @Override
    public void initialize(StatusConstraint constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if(enumValues != null)
        {
            for(Object enumValue:enumValues)
            {
                if(value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString())))
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
