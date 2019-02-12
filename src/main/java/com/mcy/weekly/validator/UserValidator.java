package com.mcy.weekly.validator;


import com.mcy.weekly.pojo.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        //专业班级 null or right
        if (user.getUserName() != null && !user.getUserName().equals("") && user.getUserName().length() > 16){
            errors.rejectValue("userName", null,"用户名格式不正确");
        }
        if (user.getProfessionalClass() != null && !user.getProfessionalClass().equals("") && !User.isPClass(user.getProfessionalClass())){
            errors.rejectValue("professionalClass", null,"专业班级格式不正确");
        }
        //手机号
        if (user.getTel() != null && !user.getTel().equals("") && !User.isTel(user.getTel())){
            errors.rejectValue("tel", null,"手机号格式不正确");
        }
        //地址
        if (user.getAddress() != null && !user.getAddress().equals("") && !User.isAddress(user.getAddress())){
            errors.rejectValue("address", null,"地址格式不正确");
        }
    }
}
