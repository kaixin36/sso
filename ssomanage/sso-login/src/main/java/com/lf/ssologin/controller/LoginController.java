package com.lf.ssologin.controller;

import com.lf.ssologin.entity.User;
import com.lf.ssologin.util.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

    /**
     * 登录
     *
     * @param user
     * @param httpSession
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public String doLogin(User user, HttpSession httpSession, HttpServletResponse response) {
        String sourceUrl = (String) httpSession.getAttribute("sourceUrl");

        if ("liufan".equals(user.username) && "111111".equals(user.password)) {
            //登录成功，保存用户信息
            String token = UUID.randomUUID().toString();
            LoginCacheUtil.loginUser.put(token, user);
            LoginCacheUtil.loginUser.put(token, user);
            //写入cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setDomain("codeshop.com");
            response.addCookie(cookie);

        } else {
            //登录失败,跳转到登录页面
            httpSession.setAttribute("msg", "登录失败!");
            return "login";
        }
        //重定向到source url地址
        return "redirect:" + sourceUrl;
    }

    /**
     * 通过token获取登录用户信息
     *
     * @param token
     * @return
     */
    @RequestMapping("userInfo")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token) {
        if (!StringUtils.isEmpty(token)) {
            User user = LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
}
