package com.disconf.web.mapper;

import com.disconf.web.entity.UserEntity;
import com.disconf.web.search.UserSearch;

import java.util.List;

public interface UserEntityMapper extends BaseMapper<UserEntity> {

    UserEntity selectByName(String userName);


}