package com.mcy.weekly.interceptor;

import com.mcy.weekly.pojo.StatusEnum;
import com.mcy.weekly.pojo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

//判断是否为管理员
public class AdministratorInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user.getStatus() != StatusEnum.ADMINISTRATOR){
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            String str = "{\"msg\":\"用户非管理员\",\"success\":\"false\",\"code\":\"1001\"}";
            out.println(str);
            if (out != null)
                out.close();
            return false;
        }
        return true;
    }
}
