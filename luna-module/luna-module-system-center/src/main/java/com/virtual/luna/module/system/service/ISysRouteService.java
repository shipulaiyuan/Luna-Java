package com.virtual.luna.module.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.virtual.luna.infra.system.domain.SysRoute;
import com.virtual.luna.module.system.vo.SysRouteVo;

import java.util.List;

public interface ISysRouteService extends IService<SysRoute> {

    List<SysRouteVo> getRoutes(SysRoute sysRoute);

    int insertRoute(SysRouteVo sysRouteDto);

    int updateRoute(SysRouteVo sysRouteDto);

    int deleteRoute(Long id);
}

