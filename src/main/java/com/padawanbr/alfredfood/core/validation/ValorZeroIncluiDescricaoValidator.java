package com.padawanbr.alfredfood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valueField;
    private String descriptionField;
    private String mandatoryDescription;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valueField = constraintAnnotation.valueField();
        this.descriptionField = constraintAnnotation.descriptionField();
        this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;

        try {
            final BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), valueField).getReadMethod().invoke(object);
            final String descricao  = (String) BeanUtils.getPropertyDescriptor(object.getClass(), descriptionField).getReadMethod().invoke(object);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0) {
                isValid = descricao.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
            }

            return isValid;
        } catch (Exception e) {
           throw new ValidationException(e);
        }

    }
}
