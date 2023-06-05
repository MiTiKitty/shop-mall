package top.itcat.mall.admin.validator;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @className: FlagValidatorClass <br/>
 * @description: 自定义状态约束校验器 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Integer> {

    private String[] values;

    @Override
    public void initialize(FlagValidator constraintAnnotation) {
        this.values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        }
        for (String s : values) {
            if (StrUtil.equals(s, String.valueOf(value))) {
                return true;
            }
        }
        return false;
    }
}
