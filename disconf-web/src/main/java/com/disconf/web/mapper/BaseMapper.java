package com.disconf.web.mapper;

import com.disconf.web.entity.BaseEntity;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/7
 */
public interface BaseMapper<T extends BaseEntity> {

    int insert(T entity);

    int delete(Long id);

    int update(T entity);

    List<T> selectAll();

    T selectById(Long id);

    //部分字段模糊查询
    List<T> selectByParam(T entity);

    Integer selectCountByParam(T entity);



}
