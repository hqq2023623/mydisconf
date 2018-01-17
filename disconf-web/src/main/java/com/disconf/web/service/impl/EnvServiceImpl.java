package com.disconf.web.service.impl;

import com.disconf.web.entity.EnvEntity;
import com.disconf.web.mapper.EnvEntityMapper;
import com.disconf.web.service.IEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/8
 */
@Service
public class EnvServiceImpl implements IEnvService {


    @Autowired
    private EnvEntityMapper envEntityMapper;

    @Override
    public EnvEntity selectById(Long envId) {
        return envEntityMapper.selectById(envId);
    }

    @Override
    public List<EnvEntity> selectAll() {
        return envEntityMapper.selectAll();
    }
}
