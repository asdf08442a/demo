package com.jiedaibao.demo.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class S2Validator implements ConstraintValidator<S2, String> {

    @Override
    public void initialize(S2 arg0) {

    }

    @Override
    public boolean isValid(String vaule, ConstraintValidatorContext context) {
        if ("day".equals(vaule) || "week".equals(vaule) || "month".equals(vaule)
                || "quarter".equals(vaule) ||"year".equals(vaule)) {
            return true;
        }else{
            return false;
        }
    }
}
