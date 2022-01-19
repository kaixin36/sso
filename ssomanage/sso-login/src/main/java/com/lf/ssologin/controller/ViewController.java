package com.lf.ssologin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping("/login")
    public String toLogin(@RequestParam String sourceUrl, HttpSession httpSession) {
        // sourceUrl合法性校验
        httpSession.setAttribute("sourceUrl",sourceUrl);
        return "login";
    }
}
