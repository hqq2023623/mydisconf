package com.disconf.web.service.impl;

import com.disconf.web.mapper.UserRoleEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.disconf.web.entity.UserRoleEntity;
import com.disconf.web.service.IUserRoleService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzj
 * @date 2018/1/4
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private UserRoleEntityMapper userRoleEntityMapper;

    @Override
    public List<UserRoleEntity> selectAll() {
        return userRoleEntityMapper.selectAll();
    }

    @Override
    public void addUserRole(UserRoleEntity userRole) {
        Map<String, Long> map = new HashMap<>();
        map.put("userId", userRole.getUserId());
        map.put("roleId", userRole.getRoleId());
        UserRoleEntity userRoleResource = userRoleEntityMapper.getUserRoleByUserIdAndRoleId(map);
        if (userRoleResource != null) {//已绑定过的用户角色不需再绑定
            return;
        }
        userRoleEntityMapper.insertSelective(userRole);
    }

    @Override
    public void delUserRoles(long id) {
        userRoleEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(UserRoleEntity userRole) {
        if (userRole.getId() != null) {
            userRole.setUpdateTime(new Date());
            userRoleEntityMapper.updateByPrimaryKey(userRole);
        }
    }

    @Override
    public List<UserRoleEntity> getRoleListByUserId(long userId) {
        return userRoleEntityMapper.getRoleListByUserId(userId);
    }

}
