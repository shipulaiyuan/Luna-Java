package com.virtual.luna.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virtual.luna.framework.web.domin.PageResult;
import com.virtual.luna.infra.system.domain.SysConfig;
import com.virtual.luna.module.system.vo.SysConfigInsertVo;
import com.virtual.luna.module.system.vo.SysConfigQueryVo;

import java.util.List;

public interface ISysConfigService extends IService<SysConfig> {
    SysConfig selectConfigByKey(String configKey);

    PageResult<SysConfig> selectList(SysConfigQueryVo sysConfigVo);

    int insertSysConfig(SysConfigInsertVo sysConfigInsertVo);

    int deleteSysConfig(Long id);

    int updateSysConfig(SysConfigInsertVo sysConfigInsertVo);

    List<String> getLabelList();

    PageResult<SysConfig> selectQueryParameters(String configLabel,String queryParameters, Integer pageNum, Integer pageSize);
}
