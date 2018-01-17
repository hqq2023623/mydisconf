package com.disconf.web.shiro;

import com.disconf.web.entity.UserEntity;
import com.disconf.web.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.disconf.web.util.Md5Util;

/**
 * @author lzj
 * @date 2018/1/4
 */
@Component("credentialsMatcher")
public class CustomCredentialsMatcher extends HashedCredentialsMatcher {


    private Logger logger = LoggerFactory.getLogger(CustomCredentialsMatcher.class);

    @Autowired
    private IUserService userService;

    /**
     * doCredentialsMatch被realm调用，用来判断Credentials是否匹配 token是从前台传递过来的待验证的对象
     * info是realm获取的数据，用来匹配
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object o1 = token.getCredentials();
        Object o2 = info.getCredentials();

        String pwd1 = new String((char[]) o1);
        pwd1 = Md5Util.encode(pwd1);
        String pwd2 = o2.toString();
        Session session = SecurityUtils.getSubject().getSession();
        if (!pwd1.equals(pwd2)) {
            session.setAttribute("loginerror", "error");
        } else {
            session.setAttribute("loginerror", "success");
        }
        return true;
    }

}