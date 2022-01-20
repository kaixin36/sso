package com.lf.ssomain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class ViewController {
    @Autowired
    private RestTemplate restTemplate;

    private final String LOGIN_INFO_ADDRESS = "http://login.codeshop.com:9000/userInfo?token=";

    @RequestMapping("/index")
    public String toIndex(@CookieValue(required = false, value = "token") Cookie cookie, HttpSession httpSession) {
        if (cookie != null) {
            String token = cookie.getValue();
            if (!StringUtils.isEmpty(token)) {
                Map resultMap = restTemplate.getForObject(LOGIN_INFO_ADDRESS + token, Map.class);
                httpSession.setAttribute("loginUser",resultMap);
            }
        }
        return "index";
    }
}
