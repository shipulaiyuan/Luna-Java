package com.virtual.luna.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virtual.luna.infra.system.domain.SysContainer;

import java.util.List;

public interface ISysContainerService extends IService<SysContainer> {

    List<SysContainer> getContainers(SysContainer sysContainer);


    int insertContainer(SysContainer sysContainer) throws Exception;

    int updateContainer(SysContainer sysContainer);

    int deleteContainer(Long id);
}

