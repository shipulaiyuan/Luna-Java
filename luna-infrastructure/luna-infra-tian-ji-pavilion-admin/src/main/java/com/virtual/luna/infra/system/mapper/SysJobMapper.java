package com.virtual.luna.infra.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.virtual.luna.infra.system.domain.SysJob;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysJobMapper extends BaseMapper<SysJob> {
}
