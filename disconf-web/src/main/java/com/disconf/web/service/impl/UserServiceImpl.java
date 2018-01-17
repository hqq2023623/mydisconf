package com.disconf.web.service.impl;

import com.disconf.web.mapper.UserEntityMapper;
import com.disconf.web.mapper.UserRoleEntityMapper;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.redis.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.disconf.core.common.constants.DisconfConstant;
import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.UserRoleEntity;
import com.disconf.web.service.IUserService;
import com.disconf.web.util.Md5Util;

import java.util.Date;
import java.util.List;

/**
 * @author lzj
 * @date 2018/1/4
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private UserRoleEntityMapper userRoleEntityMapper;

    @Autowired
    private RedisClient redisClient;

    @Override
    public List<UserEntity> selectAll() {
        return userEntityMapper.selectAll();
    }

    @Override
    public Response<String> addUser(UserEntity user) {
        String userName = user.getUserName();
        UserEntity dbUser = userEntityMapper.selectByName(userName);
        if (dbUser != null) {
            return Response.result(Response.Status.FAILURE.getCode(), "用户名已存在！");
        }
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        user.setCreator(currentUser.getUserName());
        user.setUpdater(currentUser.getUserName());
        //默认密码123456
        String enCodePwd = Md5Util.encode(DisconfConstant.DEFAULT_PASSWORD);
        user.setPassword(enCodePwd);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        try {
            userEntityMapper.insert(user);
        } catch (Exception e) {
            logger.error("add user exception,user=" + user, e);
        }
        return Response.success();
    }

    @Override
    public Response<String> delete(Long id) {
        List<UserRoleEntity> userRoleList = userRoleEntityMapper.getRoleListByUserId(id);
        if (!CollectionUtils.isEmpty(userRoleList)) {
            logger.warn("cannot delete user id is {}, some roles bind to this user", id);
            Response.fail("删除失败，请先删除相关的用户绑定！");
        }
        userEntityMapper.delete(id);
        return Response.success("已删除");
    }

    @Override
    public UserEntity selectByName(String userName) {
        UserEntity user = redisClient.get("selectByName" + userName) == null ? null : (UserEntity) redisClient.get("selectByName" + userName);
        if (user == null) {
            user = userEntityMapper.selectByName(userName);
            redisClient.put(userName, 30 * 60 * 1000, user);
        }
        return user;
    }

    @Override
    public SearchResult<UserEntity> selectByParam(UserEntity userEntity) {
        List<UserEntity> userList = userEntityMapper.selectByParam(userEntity);
        int total = userEntityMapper.selectCountByParam(userEntity);
        return new SearchResult<>(total, userList);
    }

    @Override
    public UserEntity selectById(Long id) {
        return userEntityMapper.selectById(id);
    }

    @Override
    public Response<String> update(UserEntity user) {
        UserEntity localUser = userEntityMapper.selectByName(user.getUserName());
        if (localUser != null && !localUser.getId().equals(user.getId())) {
            return Response.result(Response.Status.FAILURE.getCode(), "当前用户名已存在！");
        }
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        user.setUpdater(currentUser.getUserName());
        userEntityMapper.update(user);
        //更新用户角色表
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUserId(user.getId());
        userRole.setUserName(user.getUserName());
        userRole.setUpdater(user.getUpdater());
        userRoleEntityMapper.updateUserRoleByUserId(userRole);
        return Response.success();
    }

    @Override
    public String resetPwd(long userId, String updater) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setPassword(Md5Util.encode(DisconfConstant.DEFAULT_PASSWORD));
        userEntity.setUpdater(updater);
        int count = userEntityMapper.update(userEntity);
        return count == 1 ? DisconfConstant.DEFAULT_PASSWORD : "resetPwd fail";
    }

    @Override
    public Response<String> updatePwd(String pwd, String confirmPwd, long userId) {
        if (StringUtils.isBlank(pwd)) {
            return Response.result(Response.Status.FAILURE.getCode(), "请输入新密码");
        }
        if (StringUtils.isBlank(confirmPwd)) {
            return Response.result(Response.Status.FAILURE.getCode(), "请输入确认密码");
        }
        if (!StringUtils.equals(pwd, confirmPwd)) {
            return Response.result(Response.Status.FAILURE.getCode(), "两次密码输入不一致");
        }
        UserEntity user = userEntityMapper.selectById(userId);
        user.setUpdater(user.getUserName());
        user.setUpdateTime(new Date());
        String enCodePwd = Md5Util.encode(pwd);
        user.setPassword(enCodePwd);
        userEntityMapper.update(user);
        return Response.success();
    }

}
