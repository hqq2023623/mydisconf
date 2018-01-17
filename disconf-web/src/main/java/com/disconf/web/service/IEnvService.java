package com.disconf.web.service;

import com.disconf.web.entity.EnvEntity;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/8
 */
public interface IEnvService {

    EnvEntity selectById(Long envId);

    List<EnvEntity> selectAll();

}
