package com.mcy.weekly.controller.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice("com.mcy.weekly.controller")
public class CommonControllerAdvice {

    private Logger log = LogManager.getLogger(CommonControllerAdvice.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Map<String, Object> exception(Exception e){
        log.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("code", 5000);
        map.put("msg", "后端处理异常,可能参数输入参数格式错误,或后端出错");
        map.put("msg_detailMessage", e.getMessage());
        return map;
    }
}
