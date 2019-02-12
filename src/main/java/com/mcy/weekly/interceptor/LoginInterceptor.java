package com.mcy.weekly.interceptor;

import com.mcy.weekly.pojo.User;
import com.mcy.weekly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService = null;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){

            String loginCookieUserName = "";
            String loginCookiePassword = "";
            Cookie[] cookies = request.getCookies();
            if(null!=cookies){
                for(Cookie cookie : cookies){
                    //if("/".equals(cookie.getPath())){ //getPath为null
                    if("loginUserName".equals(cookie.getName())){
                        loginCookieUserName = cookie.getValue();
                    }else if("loginPassword".equals(cookie.getName())){
                        loginCookiePassword = cookie.getValue();
                    }
                    //}
                }
                if(!"".equals(loginCookieUserName) && !"".equals(loginCookiePassword)){
                    User loginUser = userService.getUserByEmailByPassword(loginCookieUserName, loginCookiePassword);
                    if (loginUser != null){
                        loginUser.setPassword(null);
                        session.setAttribute("user", loginUser);
                        return true;
                    }
                }
            }
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            String str = "{\"msg\":\"用户未登录\", \"code\":\"1000\",\"success\":\"false\"}";
            out.println(str);
            if (out != null)
            out.close();
            return false;
        }
        return true;
    }
}
