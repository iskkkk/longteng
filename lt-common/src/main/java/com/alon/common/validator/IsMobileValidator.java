package com.alon.common.validator;

import com.alon.common.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName IsMobileValidator
 * @Description 自定义手机格式校验器
 * @Author 一股清风
 * @Date 2019/5/20 14:46
 * @Version 1.0
 **/
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    //初始化
    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (required && StringUtils.isNotBlank(value)) {
                return ValidatorUtil.isMobile(value);
            } else {
                if (StringUtils.isEmpty(value)) {
                    return true;
                } else {
                    return ValidatorUtil.isMobile(value);
                }
            }
    }
}
