package com.virtual.luna.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.virtual.luna.infra.system.domain.SysClusters;

import java.util.List;

public interface ISysClustersService extends IService<SysClusters> {
    List<SysClusters> getClusters(SysClusters sysClusters);

    int updateClusters(SysClusters sysClusters);

    int insterClusters(SysClusters sysClusters);

    int deleteClusters(Long id);
}
