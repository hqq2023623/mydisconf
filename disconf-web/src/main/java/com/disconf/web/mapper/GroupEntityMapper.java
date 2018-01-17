package com.disconf.web.mapper;

import com.disconf.web.entity.GroupEntity;

public interface GroupEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupEntity record);

    int insertSelective(GroupEntity record);

    GroupEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupEntity record);

    int updateByPrimaryKey(GroupEntity record);
}