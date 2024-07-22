package com.virtual.luna.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.common.base.utils.SnowUtil;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.infra.system.domain.SysClusters;
import com.virtual.luna.infra.system.domain.SysGateway;
import com.virtual.luna.infra.system.domain.SysRoute;
import com.virtual.luna.infra.system.domain.SysRouteMatch;
import com.virtual.luna.infra.system.mapper.SysClustersMapper;
import com.virtual.luna.infra.system.mapper.SysGatewayMapper;
import com.virtual.luna.infra.system.mapper.SysRouteMapper;
import com.virtual.luna.infra.system.mapper.SysRouteMatchMapper;
import com.virtual.luna.module.system.vo.SysRouteVo;
import com.virtual.luna.module.system.service.ISysRouteService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRouteServiceImpl extends ServiceImpl<SysRouteMapper, SysRoute> implements ISysRouteService {

    @Autowired
    private SysRouteMapper sysRouteMapper;

    @Autowired
    private SysRouteMatchMapper sysRouteMatchMapper;

    @Autowired
    private SysGatewayMapper sysGatewayMapper;

    @Autowired
    private SysClustersMapper sysClustersMapper;

    @Override
    public List<SysRouteVo> getRoutes(SysRoute sysRoute) {

        List<SysRouteVo> results = new ArrayList<>();

        LambdaQueryWrapper<SysRoute> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRoute::getDelFlag,"0");

        if(ObjectUtils.isNotEmpty(sysRoute.getRouteKey())){
            lambdaQueryWrapper.eq(SysRoute::getRouteKey,sysRoute.getClustersKey());
        }

        if(ObjectUtils.isNotEmpty(sysRoute.getRouteAlias())){
            lambdaQueryWrapper.eq(SysRoute::getRouteAlias,sysRoute.getRouteAlias());
        }

        if(ObjectUtils.isNotEmpty(sysRoute.getGatewayKey())){
            lambdaQueryWrapper.eq(SysRoute::getGatewayKey,sysRoute.getGatewayKey());
        }

        List<SysRoute> sysRoutes = sysRouteMapper.selectList(lambdaQueryWrapper);

        for (SysRoute route : sysRoutes) {
            SysRouteMatch sysRouteMatch = sysRouteMatchMapper.selectOne(
                    new LambdaQueryWrapper<SysRouteMatch>().eq(SysRouteMatch::getRouteId, route.getId())
            );
            SysRouteVo sysRouteDto = new SysRouteVo();
            sysRouteDto.setSysRoute(route);
            sysRouteDto.setSysRouteMatch(sysRouteMatch);
            results.add(sysRouteDto);
        }
        return results;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertRoute(SysRouteVo sysRouteDto) {

        SysRouteMatch sysRouteMatch = sysRouteDto.getSysRouteMatch();
        SysRoute sysRoute = sysRouteDto.getSysRoute();

        String gatewayKey = sysRoute.getGatewayKey();
        SysGateway sysGateway = sysGatewayMapper.selectOne(
                new LambdaQueryWrapper<SysGateway>().eq(SysGateway::getGatewayKey, gatewayKey)
        );
        if(ObjectUtils.isEmpty(sysGateway)){
            throw new LunaException("网关不存在");
        }

        String clustersKey = sysRoute.getClustersKey();
        SysClusters sysClusters = sysClustersMapper.selectOne(
                new LambdaQueryWrapper<SysClusters>().eq(SysClusters::getClustersKey, clustersKey)
        );
        if(ObjectUtils.isEmpty(sysClusters)){
            throw new LunaException("集群不存在");
        }

        sysRoute.setRouteKey(String.valueOf(SnowUtil.nextId()));
        sysRoute.setCreateTime(DateUtils.getNowDate());

        int insertRoute  = sysRouteMapper.insert(sysRoute);

        sysRouteMatch.setCreateTime(DateUtils.getNowDate());
        sysRouteMatch.setRouteId(sysRoute.getId());
        int insertRouteMatch  = sysRouteMatchMapper.insert(sysRouteMatch);

        if (insertRoute > 0 && insertRouteMatch > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateRoute(SysRouteVo sysRouteDto) {

        SysRouteMatch sysRouteMatch = sysRouteDto.getSysRouteMatch();
        SysRoute sysRoute = sysRouteDto.getSysRoute();

        String gatewayKey = sysRoute.getGatewayKey();
        SysGateway sysGateway = sysGatewayMapper.selectOne(
                new LambdaQueryWrapper<SysGateway>().eq(SysGateway::getGatewayKey, gatewayKey)
        );
        if(ObjectUtils.isEmpty(sysGateway)){
            throw new LunaException("网关不存在");
        }

        String clustersKey = sysRoute.getClustersKey();
        SysClusters sysClusters = sysClustersMapper.selectOne(
                new LambdaQueryWrapper<SysClusters>().eq(SysClusters::getClustersKey, clustersKey)
        );
        if(ObjectUtils.isEmpty(sysClusters)){
            throw new LunaException("集群不存在");
        }

        sysRoute.setUpdateTime(DateUtils.getNowDate());
        int updateRoute = sysRouteMapper.updateById(sysRoute);

        sysRouteMatch.setUpdateTime(DateUtils.getNowDate());
        int updateRouteMatch = sysRouteMatchMapper.updateById(sysRouteMatch);

        if (updateRoute > 0 && updateRouteMatch > 0) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public int deleteRoute(Long id) {
        SysRoute sysRoute = sysRouteMapper.selectById(id);

        sysRoute.setUpdateTime(DateUtils.getNowDate());
        sysRoute.setDelFlag("2");

        return sysRouteMapper.updateById(sysRoute);
    }
}
