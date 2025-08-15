package com.ciaj.comm.utils;


import com.ciaj.comm.exception.BsRException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/6 15:51
 * @Description:
 */
@Slf4j
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws BsRException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws BsRException {
        validate(object, groups);
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @throws BsRException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object) throws BsRException {
        validate(object, null);
    }

    private static void validate(Object object, Class<?>... groups) {
        if (object != null && object instanceof Collection) {
            Collection collection = (Collection) object;
            collection.forEach(o -> {
                validate(o, groups);
            });
        } else {
            Set<ConstraintViolation<Object>> constraintViolations;
            if (groups == null) {
                constraintViolations = validator.validate(object);
            } else {
                constraintViolations = validator.validate(object, groups);
            }

            if (constraintViolations != null && !constraintViolations.isEmpty()) {
                for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                    log.error("validate has error property：{},error msg：{}", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
                }
                ConstraintViolation<Object> constraint = (ConstraintViolation<Object>) constraintViolations.iterator().next();
                throw new BsRException(constraint.getMessage());
            }
        }
    }
}
