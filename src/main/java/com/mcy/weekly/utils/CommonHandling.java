package com.mcy.weekly.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

public class CommonHandling {
    //如存在格式错误返回false
    public static boolean format(Map<String, Object> map, Errors errors){
        if (errors.hasErrors()){
            StringBuffer buffer = new StringBuffer();
            List<FieldError> errorList = errors.getFieldErrors();
            for (FieldError error :
                    errorList) {
                buffer.append(error.getField() + "和");
            }
            int lastIndexOf = buffer.lastIndexOf("和");
            String result = buffer.substring(0, lastIndexOf - 1);
            result = result + "参数格式错误(可能参数格式或者长度)";
            map.put("msg", result);
            map.put("success", false);
            map.put("code", "1004");
            return false;
        }
        return true;
    }
    public static Boolean isNull(Map<String, Object> map,Object... objects){
        StringBuffer buffer = new StringBuffer();
        for (Object o :
                objects) {
            if ( o != null &&o instanceof String)
            {
                if (o.equals("")){
                    buffer.append(o.getClass().getSimpleName());
                }
            }else if (o == null){
                buffer.append(o.getClass().getSimpleName());
            }
        }
        if (buffer.length() > 0){
            map.put("success", false);
            map.put("msg", buffer.toString() + "类型的参数不能为null");
            map.put("code","1003");
            return false;
        }
        return true;
    }
}
