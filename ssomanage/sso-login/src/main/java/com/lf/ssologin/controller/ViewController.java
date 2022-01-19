package com.lf.ssologin.controller;

import com.lf.ssologin.entity.User;
import com.lf.ssologin.util.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping("/login")
    public String toLogin(@RequestParam(required = false,defaultValue = "") String sourceUrl,
                          HttpSession httpSession,
                         @CookieValue(required = false,value = "token") Cookie cookie) {
//        if(StringUtils.isEmpty(cookie))
//        {
//            //定为到特定页面，页面
//            sourceUrl = "http://www.codeshop.com:9010";
//        }

        // 应该还有sourceUrl合法性校验

        //写入session
        httpSession.setAttribute("sourceUrl",sourceUrl);

        //已登录的用户再次访问登录系统时，直接重定向
        if(cookie!=null)
        {
            String token =cookie.getValue();
            User user = LoginCacheUtil.loginUser.get(token);
            if(user !=null)
            {
                //这里还应该把user信息写如Cooke中，再进行从定向
                return "redirect:"+sourceUrl;
            }
        }


        return "login";
    }
}
