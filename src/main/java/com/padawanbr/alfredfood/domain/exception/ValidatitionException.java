package com.padawanbr.alfredfood.domain.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidatitionException extends RuntimeException {

    private BindingResult bindingResult;

}
