package com.disconf.web.service;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.search.UserSearch;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/4
 */
public interface IUserService {

    Response<String> delete(Long id);

    Response<String> update(UserEntity entity);

    UserEntity selectById(Long id);

    List<UserEntity> selectAll();

    SearchResult<UserEntity> selectByParam(UserEntity entity);

    UserEntity selectByName(String userName);

    String resetPwd(long userId, String updater);

    Response<String> updatePwd(String pwd, String confirmPwd, long userId);

    Response<String> addUser(UserEntity userEntity);


}
