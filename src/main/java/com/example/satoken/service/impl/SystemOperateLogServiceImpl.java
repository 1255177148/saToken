package com.example.satoken.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.satoken.entity.SystemOperateLog;
import com.example.satoken.mapper.SystemOperateLogMapper;
import com.example.satoken.service.SystemOperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SystemOperateLogServiceImpl extends ServiceImpl<SystemOperateLogMapper, SystemOperateLog> implements SystemOperateLogService {
}
