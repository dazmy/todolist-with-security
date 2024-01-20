package com.dazmy.todolist.security.service.implementation;

import com.dazmy.todolist.security.service.ValidationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    private Validator validator;
    @Override
    public void validate(Object o) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
