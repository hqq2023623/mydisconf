package com.disconf.web.controller;

import com.disconf.web.redis.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.service.IUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lzj
 * @date 2018/1/3
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/")
    public String rot() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginIndex(HttpServletRequest request, ModelMap model) {
        Object object = SecurityUtils.getSubject().getPrincipal();
        if (object != null) {
            return "/index";
        }
        UserEntity user = userService.selectByName(request.getParameter("username"));
        String loginInfo = "";
        if(user == null) {
            loginInfo = "您的用户名或密码错误";
        }
        String msg = (String) request.getAttribute("shiroLoginFailure");
        if (null != msg) {
            if (IncorrectCredentialsException.class.getName().equals(msg)) {
                loginInfo = "该用户名或密码错误";
            } else if (UnknownAccountException.class.getName().equals(msg)) {
                loginInfo = "该用户名或密码错误";
            } else if (StringUtils.isNotBlank(msg)) {
                loginInfo = "其他错误：" + msg;
            }
        }
        model.addAttribute("logininfo", loginInfo);
        return "/login";

    }


    @RequestMapping("/auth/unauthorized")
    public String unauthorized(UserEntity user){
        return "unauthorized" ;
    }

}
