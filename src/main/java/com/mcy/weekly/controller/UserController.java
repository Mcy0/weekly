package com.mcy.weekly.controller;

import com.mcy.weekly.pojo.StateEnum;
import com.mcy.weekly.pojo.StatusEnum;
import com.mcy.weekly.pojo.User;
import com.mcy.weekly.pojo.UserParams;
import com.mcy.weekly.service.UserService;
import com.mcy.weekly.utils.CommonHandling;
import com.mcy.weekly.utils.MD5;
import com.mcy.weekly.utils.My;
import com.mcy.weekly.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService = null;


    /**
     * 登录
     *
     * @param email 邮箱
     * @param password 密码
     * @param session
     * @param response
     * @return json
     */
    //登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(String email, String password, HttpSession session,
                                     HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        //判空
        if (null == email || password == null || email.equals("") || password.equals("")){//账户密码参数为空返回
            map.put("success", false);
            map.put("msg", "登录失败，邮箱密码为空");
            map.put("code", "1003");
            return map;
        }
        if (!(User.isEmail(email) && User.isPassword(password))){//格式验证
            map.put("success", false);
            map.put("msg", "登录失败，邮箱或者密码格式不对");
            map.put("code", "1004");
            return map;
        }
        //md5加密
        String str = password = MD5.MD5(password);
        User user = userService.getUserByEmailByPassword(email, password);
        if (null != user){
//            user.setPassword(null);
            session.setAttribute("user", user);//session存入用户user
        }else {//账户密码错误返回
            map.put("success", false);
            map.put("msg", "登录失败，账户或者密码错误");
            map.put("code", "3000");
            return map;
        }
        map.put("user", user);
        map.put("success", true);
        map.put("code", "2000");
        map.put("msg", "登录成功");


        //设置Cookie
        if (My.getAutoLogin() > 0){
            int autoLoginTime = My.getAutoLogin() * 24 * 60 * 60;
            Cookie userNameCookie = new Cookie("loginUserName", user.getEmail());
            Cookie passwordCookie = new Cookie("loginPassword", str);
            userNameCookie.setMaxAge(autoLoginTime);
//            userNameCookie.setPath("/");
            passwordCookie.setMaxAge(autoLoginTime);
//            passwordCookie.setPath("/");
            response.addCookie(userNameCookie);
            response.addCookie(passwordCookie);
        }

        return map;
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        //删除登录cookie
        Cookie userNameCookie = new Cookie("loginUserName", user.getUserName());
        Cookie passwordCookie = new Cookie("loginPassword", user.getPassword());
        userNameCookie.setMaxAge(0);
//        userNameCookie.setPath("/");
        passwordCookie.setMaxAge(0);
//        passwordCookie.setPath("/");
        response.addCookie(userNameCookie);
        response.addCookie(passwordCookie);
        request.getSession().removeAttribute("user");
        map.put("success", true);
        map.put("msg", "退出出成功, 删除Cookie");
        map.put("code", "2000");
        return map;

    }
    //获取个人资料,及时更新
    @ResponseBody
    @RequestMapping("/getUser")
    public Map<String, Object> getUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        user = userService.getUserById(user.getId());
        session.setAttribute("user", user);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("code", "2000");
        map.put("user", user);
        return map;
    }

    //自定义验证器
    @InitBinder("user")
    public void initBinder(DataBinder binder){
        binder.addValidators(new UserValidator());
    }
    //修改资料
    @ResponseBody
    @RequestMapping("/updateUser")
    public Map<String, Object> updateUser(@Valid User user, Errors errors, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        //判断是否传入参数
        if (user.getLearningDirection() == null &&
                user.getTel() == null &&
                user.getProfessionalClass() == null &&
                user.getLearningDirection() == null &&
                user.getAddress() == null &&
                user.getState() == null &&
                user.getUserName() == null){
            map.put("msg", "所有参数为空,不更新");
            map.put("success", true);
            map.put("code", "2000");
            return map;
        } else if (!CommonHandling.format(map, errors)){
            return map;
        }
        User sessionUser = (User) session.getAttribute("user");
        user.setId(sessionUser.getId());
        userService.updateUser(user);
        //更新资料
        user = userService.getUserById(user.getId());
        session.setAttribute("user", user);
        map.put("success", true);
        map.put("msg", "资料修改成功");
        map.put("code", "2000");
        return map;
    }
    //获取同事列表
    //使用json接受参数
    @RequestMapping("/getAllUser")
    @ResponseBody
    public Map<String, Object> getAllUser(@RequestBody UserParams userParams){
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userService.getUserByUserParams(userParams);
        map.put("success", true);
        map.put("users", userList);
        map.put("msg", "获取成功");
        map.put("code", "2000");
        map.put("total", userParams.getPageParams().getTotal());
        map.put("total", userParams.getPageParams().getTotalPage());
        return map;
    }

    //管理员添加用户 默认添加非管理员 密码123456
    @RequestMapping("/addUserSim")
    @ResponseBody
    public Map<String, Object> assUserSim(String email, String password, StatusEnum status, StateEnum state){
        Map<String, Object> map = new HashMap<>();
        if (email == null){
            map.put("success", false);
            map.put("msg", "email 为  null");
            map.put("code", "1003");
            return map;
        }
        //判断格式
        if (!User.isEmail(email) || (password != null && !password.equals("") && !User.isPassword(password))){
            map.put("success", false);
            map.put("msg", "email or password 格式不正确");
            map.put("code", "1004");
            return map;
        }
        //判断是否已经存在
        if (userService.getUserByEmail(email) != null){
            map.put("success", false);
            map.put("msg", "email 已经存在");
            map.put("code", "3001");
            return map;
        }
        status = status == null ? StatusEnum.NONADMINISTRATOR : status;//默认非管理员
        state = state == null ? StateEnum.IN_SCHOOL : state;//默认在校
        password = password == null ? "123456" : password;// 默认密码 123456
        password = MD5.MD5(password);//MD5加密
        int count = userService.insertUserSim(email, password, status, state);
        map.put("success", true);
        map.put("code", "2000");
        map.put("msg", "添加成功");
        return map;
    }
    //管理员删除用户


    //管理员修改用户资料
    @RequestMapping("/updateUserSim")
    @ResponseBody
    public Map<String, Object> updateUserSim(@RequestParam(value = "email", required = false) String email, StatusEnum status,
                                             @RequestParam(value = "state", required = false) StateEnum state,
                                             @RequestParam(value = "reset", defaultValue = "false", required = false) boolean reset){
        Map<String, Object> map = new HashMap<>();
        if (email == null){
            map.put("success", false);
            map.put("msg", "email 为 null");
            map.put("code", "1003");
            return map;
        }

        if (!User.isEmail(email)){
            map.put("success", false);
            map.put("msg", "email格式错误");
            map.put("code", "1004");
            return map;
        }
        String password = null;
        if (reset == true){
            password = MD5.MD5("123456");
        }
        int count = userService.updateUserSim(email, status, state, password);
        if (count == 0){
            map.put("success", false);
            map.put("msg", "用户不存在失败");
            map.put("code", "3002");
            return map;
        }
        map.put("success", true);
        map.put("msg", "成功");
        map.put("code", "2000");
        return map;
    }
    //修改密码
    @RequestMapping("/updateUserPassword")
    @ResponseBody
    public Map<String, Object> updateUserPassword(String oldPassword, String newPassword,
                                                  HttpSession session){
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        if (oldPassword == null || newPassword == null){
            map.put("success", false);
            map.put("msg", "oldPassword or newPassword 为 null");
            map.put("code", "1003");
            return map;
        }
        if (!User.isPassword(oldPassword) || !User.isPassword(newPassword)){
            map.put("success", false);
            map.put("msg", "oldPassword or newPassword 格式不正确");
            map.put("code", "1004");
            return map;
        }
        String email = user.getEmail();
        newPassword = MD5.MD5(newPassword);
        oldPassword = MD5.MD5(oldPassword);
        if (userService.getUserByEmailByPassword(email, oldPassword) == null){
            map.put("success", false);
            map.put("msg", "待更新密码错误");
            map.put("code", "3003");
            return map;
        }
        int id = user.getId();
        userService.updateUserPassword(id, newPassword);
        map.put("success", true);
        map.put("msg", "成功");
        map.put("code", "2000");
        return map;
    }
    //忘记密码
    @RequestMapping("/forgotPassword")
    @ResponseBody
    public Map<String, Object> forgotPassword(String email, String password, String code, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        if (email == null || "".equals(email) || password == null || "".equals(password) || code == null || "".equals(code)){
            map.put("success", false);
            map.put("msg", "email or password or code 为 null");
            map.put("code", "1003");
            return map;
        }
        if (!User.isEmail(email) || !User.isPassword(password) || code.length() != 4){
            map.put("success", false);
            map.put("msg", "email or password or code 格式不对");
            map.put("code", "1004");
            return map;
        }
        User user = userService.getUserByEmail(email);
        if (user == null){
            map.put("success", false);
            map.put("msg", "用户不存在");
            map.put("code", "3002");
            return map;
        }

        Integer codeCount = (Integer) session.getAttribute("codeCount");
        String str = (String) session.getAttribute("code");
        if (codeCount == null || str == null){
            map.put("success", false);
            map.put("msg", "还没有请求验证码");
            map.put("code", "4000");
            return map;
        }
        if (codeCount <= 0){
            session.removeAttribute("code");
            session.removeAttribute("codeCount");
            map.put("success", false);
            map.put("msg", "验证已超过3次");
            map.put("code", "3004");
            return map;
        }

        session.setAttribute("codeCount", codeCount - 1);
        if (!str.equals(code)){
            map.put("success", false);
            map.put("msg", "验证失败");
            map.put("code", "4000");
            return map;
        }
        session.removeAttribute("code");
        session.removeAttribute("codeCount");
        map.put("success", true);
        map.put("msg", "成功");
        map.put("code", "2000");
        password = MD5.MD5(password);//加密
        userService.updateUserPassword(user.getId(), password);
        return map;
    }
    //测试
    @RequestMapping("/deleteUser")
    @ResponseBody
    public Map<String, Object> deleteUser(Integer id, HttpSession session){
        //判null
        Map<String, Object> map = new HashMap<>();
        if (CommonHandling.isNull(map, id) == false){
            return map;
        }
        int count = userService.deleteUserById(id);
        if (count == 0){
            map.put("success", false);
            map.put("code", "3002");
            map.put("msg", "用户不存在");
            return map;
        }
        map.put("success", true);
        map.put("code", "2000");
        map.put("msg", "成功");
        return map;
    }
}
