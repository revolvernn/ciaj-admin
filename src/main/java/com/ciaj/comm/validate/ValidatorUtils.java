package com.ciaj.comm.validate;

import com.ciaj.comm.exception.BsRException;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/6 15:51
 * @Description:
 */
@Log4j2
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
     *
     * @throws BsRException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws BsRException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);

        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                log.error("validate has error property：{},error msg：{}", constraintViolation.getPropertyPath(),constraintViolation.getMessage());
            }
            ConstraintViolation<Object> constraint = (ConstraintViolation<Object>) constraintViolations.iterator().next();
            throw new BsRException(constraint.getMessage());
        }
    }
}
