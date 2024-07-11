package com.virtual.luna.module.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.virtual.luna.infra.system.domain.SysGateway;

import java.util.HashMap;
import java.util.List;

public interface ISysGatewayService extends IService<SysGateway> {

    HashMap<String, Object> getGatewayConfig(String gatewayKey);

    List<SysGateway> getList(SysGateway sysGateway);

    int insertGateway(SysGateway sysGateway);

    int updateGateway(SysGateway sysGateway);

    int deleteGateway(Long id);

    int refreshConfig(String gatewayKey);
}

