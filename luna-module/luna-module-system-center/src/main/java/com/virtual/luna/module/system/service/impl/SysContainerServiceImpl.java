package com.virtual.luna.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.common.base.utils.SnowUtil;
import com.virtual.luna.common.base.utils.StringUtils;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.infra.system.domain.SysClusters;
import com.virtual.luna.infra.system.domain.SysContainer;
import com.virtual.luna.infra.system.mapper.SysClustersMapper;
import com.virtual.luna.infra.system.mapper.SysContainerMapper;
import com.virtual.luna.module.system.service.ISysContainerService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysContainerServiceImpl extends ServiceImpl<SysContainerMapper, SysContainer> implements ISysContainerService {

    @Autowired
    private SysContainerMapper sysContainerMapper;

    @Autowired
    private SysClustersMapper sysClustersMapper;

    @Override
    public List<SysContainer> getContainers(SysContainer sysContainer) {
        LambdaQueryWrapper<SysContainer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysContainer::getDelFlag, "0");

        if (ObjectUtils.isNotEmpty(sysContainer.getId())) {
            lambdaQueryWrapper.eq(SysContainer::getId, sysContainer.getId());
        }

        if (StringUtils.isNotEmpty(sysContainer.getContainerAlias())) {
            lambdaQueryWrapper.like(SysContainer::getContainerAlias, sysContainer.getContainerAlias());
        }

        if (StringUtils.isNotEmpty(sysContainer.getIsOpenHealthCheck())) {
            lambdaQueryWrapper.eq(SysContainer::getIsOpenHealthCheck, sysContainer.getIsOpenHealthCheck());
        }

        if (StringUtils.isNotEmpty(sysContainer.getContainerState())) {
            lambdaQueryWrapper.eq(SysContainer::getContainerState, sysContainer.getContainerState());
        }

        if (StringUtils.isNotEmpty(sysContainer.getClustersKey())) {
            lambdaQueryWrapper.eq(SysContainer::getClustersKey, sysContainer.getClustersKey());
        }


        List<SysContainer> sysContainers = sysContainerMapper.selectList(lambdaQueryWrapper);
        return sysContainers;
    }

    @Override
    public int insertContainer(SysContainer sysContainer) throws Exception {

        if(ObjectUtils.isEmpty(sysContainer.getCreateTime())){
            sysContainer.setCreateTime(DateUtils.getNowDate());
        }

        String clustersKey = sysContainer.getClustersKey();

        LambdaQueryWrapper<SysClusters> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysClusters::getDelFlag, "0");
        lambdaQueryWrapper.eq(SysClusters::getClustersKey,clustersKey);
        List<SysClusters> sysClusters = sysClustersMapper.selectList(lambdaQueryWrapper);

        if(ObjectUtils.isEmpty(sysClusters)){
            throw new LunaException("ClustersKey 不存在");
        }

        if(1 != sysClusters.size()){
            throw new LunaException("系统异常，集群不唯一");
        }

        LambdaQueryWrapper<SysContainer> lambda = new LambdaQueryWrapper<>();
        lambda.eq(SysContainer::getDelFlag, "0");
        lambda.eq(SysContainer::getContainerIp,sysContainer.getContainerIp());
        lambda.eq(SysContainer::getContainerPort,sysContainer.getContainerPort());
        List<SysContainer> sysContainers = sysContainerMapper.selectList(lambda);

        if(ObjectUtils.isNotEmpty(sysContainers)){
            throw new LunaException("此容器已存在");
        }

        sysContainer.setContainerKey(String.valueOf(SnowUtil.nextId()));

        return sysContainerMapper.insert(sysContainer);
    }

    @Override
    public int updateContainer(SysContainer sysContainer) {

        if(ObjectUtils.isEmpty(sysContainer.getUpdateTime())){
            sysContainer.setUpdateTime(DateUtils.getNowDate());
        }

        return sysContainerMapper.updateById(sysContainer);
    }

    @Override
    public int deleteContainer(Long id) {

        SysContainer sysContainer = sysContainerMapper.selectById(id);

        sysContainer.setUpdateTime(DateUtils.getNowDate());
        sysContainer.setDelFlag("2");

        return sysContainerMapper.updateById(sysContainer);
    }
}
