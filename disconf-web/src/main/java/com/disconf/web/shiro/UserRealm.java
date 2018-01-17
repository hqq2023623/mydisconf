package com.disconf.web.shiro;

import com.disconf.web.service.IRolePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.disconf.web.entity.RolePermission;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.entity.UserRoleEntity;
import com.disconf.web.redis.RedisClient;
import com.disconf.web.service.IUserRoleService;
import com.disconf.web.service.IUserService;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRolePermissionService rolePermissionService;
    //
    @Autowired
    private RedisClient redisClient;

    /**
     * 身份认证 返回一个包含AuthenticationInfo的子类用于身份验证
     * SimpleAuthenticationInfo表示用于认证身份的数据源
     * 如果要使用salt进行加密时这里的AuthenticationInfo应该是SaltedAuthenticationInfo的子类
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 查出是否有此用户
        UserEntity user = userService.selectByName(token.getUsername());
        if (user != null) {
            logger.warn("token current ip is {}, current user is {}", token.getHost(), token.getUsername());
            // 若存在，将此用户存放到登录认证info中
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return simpleAuthenticationInfo;
        } else {
            throw new UnknownAccountException();
        }
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录时输入的用户名
        UserEntity user = (UserEntity) principalCollection.getPrimaryPrincipal();
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user != null) {
            // 根据userId获取Role
            List<UserRoleEntity> userRoles = userRoleService.getRoleListByUserId(user.getId());
            for (UserRoleEntity userRole : userRoles) {
                List<RolePermission> ps = rolePermissionService.getPermissionListByRoleId(userRole.getRoleId());
                for (RolePermission p : ps) {
                    if (StringUtils.isNotBlank(p.getPermissionName())) {
                        info.addStringPermission(p.getPermissionName());
                    }
                }
            }
            return info;
        }
        return null;
    }

}