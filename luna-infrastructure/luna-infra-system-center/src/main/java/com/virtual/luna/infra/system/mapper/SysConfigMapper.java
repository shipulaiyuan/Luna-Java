package com.virtual.luna.infra.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.virtual.luna.infra.system.domain.SysConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
}
