package com.virtual.luna.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.common.base.utils.SnowUtil;
import com.virtual.luna.infra.system.domain.SysClusters;
import com.virtual.luna.infra.system.domain.SysContainer;
import com.virtual.luna.infra.system.mapper.SysClustersMapper;
import com.virtual.luna.infra.system.mapper.SysContainerMapper;
import com.virtual.luna.module.system.service.ISysClustersService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysClustersServiceImpl  extends ServiceImpl<SysClustersMapper, SysClusters> implements ISysClustersService {

    @Autowired
    private SysClustersMapper sysClustersMapper;

    @Autowired
    private SysContainerMapper sysContainerMapper;

    @Override
    public List<SysClusters> getClusters(SysClusters sysClusters) {

        LambdaQueryWrapper<SysClusters> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysClusters::getDelFlag, "0");

        if (ObjectUtils.isNotEmpty(sysClusters.getId())) {
            lambdaQueryWrapper.eq(SysClusters::getId, sysClusters.getId());
        }

        if (ObjectUtils.isNotEmpty(sysClusters.getClustersAlias())) {
            lambdaQueryWrapper.like(SysClusters::getClustersAlias, sysClusters.getClustersAlias());
        }

        if (ObjectUtils.isNotEmpty(sysClusters.getIsBlacklist())) {
            lambdaQueryWrapper.eq(SysClusters::getIsBlacklist, sysClusters.getIsBlacklist());
        }

        return sysClustersMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public int updateClusters(SysClusters sysClusters) {

        if(ObjectUtils.isEmpty(sysClusters.getUpdateTime())){
            sysClusters.setUpdateTime(DateUtils.getNowDate());
        }

        return sysClustersMapper.updateById(sysClusters);
    }

    @Override
    public int insterClusters(SysClusters sysClusters) {

        if(ObjectUtils.isEmpty(sysClusters.getCreateTime())){
            sysClusters.setCreateTime(DateUtils.getNowDate());
        }

        if(ObjectUtils.isEmpty(sysClusters.getClustersKey())){
            sysClusters.setClustersKey(String.valueOf(SnowUtil.nextId()));
        }

        return sysClustersMapper.insert(sysClusters);
    }

    @Override
    public int deleteClusters(Long id) {

        SysClusters sysClusters = sysClustersMapper.selectById(id);
        String clustersKey = sysClusters.getClustersKey();

        LambdaQueryWrapper<SysContainer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysContainer::getDelFlag, "0");
        lambdaQueryWrapper.eq(SysContainer::getClustersKey,clustersKey);
        List<SysContainer> sysContainers = sysContainerMapper.selectList(lambdaQueryWrapper);

        if(ObjectUtils.isNotEmpty(sysContainers)){
            return -1;
        }

        sysClusters.setUpdateTime(DateUtils.getNowDate());
        sysClusters.setDelFlag("2");

        return sysClustersMapper.updateById(sysClusters);
    }
}
