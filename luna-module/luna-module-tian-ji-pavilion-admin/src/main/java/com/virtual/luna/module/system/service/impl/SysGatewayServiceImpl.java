package com.virtual.luna.module.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virtual.luna.common.base.constant.Constants;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.framework.redis.utils.RedisCache;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.infra.system.common.RedisConstants;
import com.virtual.luna.infra.system.domain.*;
import com.virtual.luna.infra.system.mapper.*;
import com.virtual.luna.module.system.service.ISysGatewayService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SysGatewayServiceImpl extends ServiceImpl<SysGatewayMapper, SysGateway> implements ISysGatewayService {


    @Autowired
    private SysGatewayMapper sysGatewayMapper;

    @Autowired
    private SysRouteMapper sysRouteMapper;

    @Autowired
    private SysRouteMatchMapper sysRouteMatchMapper;

    @Autowired
    private SysClustersMapper sysClustersMapper;

    @Autowired
    private SysContainerMapper sysContainerMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public HashMap<String, Object> getGatewayConfig(String gatewayKey) {

        String redisKey = RedisConstants.LUNA_GATEWAY_CONFIG + gatewayKey;

        HashMap<String, Object> cacheObject = (HashMap<String, Object>)redisCache.getCacheObject(redisKey);

        if(ObjectUtils.isNotEmpty(cacheObject)){
            return cacheObject;
        }

        HashMap<String, Object> results = databaseAcquisitionConfig(gatewayKey);

        // 缓存
        redisCache.setCacheObject(redisKey,results,24, TimeUnit.HOURS);

        return results;
    }

    /**
     * 数据库 组装网关全部配置
     * @param gatwayKey
     * @return
     */
    private HashMap<String, Object> databaseAcquisitionConfig(String gatwayKey) {
        LambdaQueryWrapper<SysGateway> lambda = new LambdaQueryWrapper<>();
        lambda.eq(SysGateway::getDelFlag, "0");
        lambda.eq(SysGateway::getGatewayKey, gatwayKey);
        SysGateway sysGateway = sysGatewayMapper.selectOne(lambda);

        if(ObjectUtils.isEmpty(sysGateway)){
            throw new LunaException("网关异常");
        }

        LambdaQueryWrapper<SysRoute> lambdaRoute = new LambdaQueryWrapper<>();
        lambdaRoute.eq(SysRoute::getDelFlag, "0");
        lambdaRoute.eq(SysRoute::getGatewayKey, gatwayKey);
        List<SysRoute> sysRoutes = sysRouteMapper.selectList(lambdaRoute);

        ArrayList<Object> routesList = new ArrayList<>();
        ArrayList<Object> clustersList = new ArrayList<>();

        for (SysRoute sysRoute : sysRoutes) {

            /**
             * ========== Routes ==========
             */

            HashMap<String, Object> routes = new HashMap<>();

            if(ObjectUtils.isEmpty(sysRoute.getRouteKey())){
                throw new LunaException("配置异常，RouteId为空");
            }

            if(ObjectUtils.isEmpty(sysRoute.getClustersKey())){
                throw new LunaException("配置异常，ClusterId为空");
            }

            routes.put("RouteId",sysRoute.getRouteKey());
            routes.put("ClusterId",sysRoute.getClustersKey());

            if(ObjectUtils.isNotEmpty(sysRoute.getRouteOrder()) && !"[]".equals(sysRoute.getRouteOrder())){
                routes.put("Order",sysRoute.getRouteOrder());
            }

            if(ObjectUtils.isNotEmpty(sysRoute.getMaxRequestBodySize()) && !"[]".equals(sysRoute.getMaxRequestBodySize())){
                routes.put("MaxRequestBodySize",sysRoute.getMaxRequestBodySize());
            }

            if(ObjectUtils.isNotEmpty(sysRoute.getAuthorizationPolicy()) && !"[]".equals(sysRoute.getAuthorizationPolicy())){
                routes.put("AuthorizationPolicy",sysRoute.getAuthorizationPolicy());
            }

            if(ObjectUtils.isNotEmpty(sysRoute.getCorsPolicy()) && !"[]".equals(sysRoute.getCorsPolicy())){
                routes.put("CorsPolicy",sysRoute.getCorsPolicy());
            }

            if(ObjectUtils.isNotEmpty(sysRoute.getRateLimiterPolicy()) && !"[]".equals(sysRoute.getRateLimiterPolicy())){
                routes.put("RateLimiterPolicy",sysRoute.getRateLimiterPolicy());
            }

            if(ObjectUtils.isNotEmpty(sysRoute.getOutputCachePolicy()) && !"[]".equals(sysRoute.getOutputCachePolicy())){
                routes.put("OutputCachePolicy",sysRoute.getOutputCachePolicy());
            }

            if(ObjectUtils.isNotEmpty(sysRoute.getTimeoutPolicy()) && !"[]".equals(sysRoute.getTimeoutPolicy())){
                routes.put("TimeoutPolicy",sysRoute.getTimeoutPolicy());
            }

            if(ObjectUtils.isNotEmpty(sysRoute.getMetaData()) && !"[]".equals(sysRoute.getMetaData())){
                routes.put("MetaData",JSONObject.parse(sysRoute.getMetaData()));
            }

            LambdaQueryWrapper<SysRouteMatch> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SysRouteMatch::getDelFlag, "0");
            lambdaQueryWrapper.eq(SysRouteMatch::getRouteId,sysRoute.getId());
            SysRouteMatch sysRouteMatch = sysRouteMatchMapper.selectOne(lambdaQueryWrapper);

            HashMap<String, Object> match = new HashMap<>();

            if(ObjectUtils.isEmpty(sysRouteMatch.getMatchPath())){
                throw new LunaException("配置异常，Path为空");
            }

            match.put("Path",sysRouteMatch.getMatchPath());

            if(ObjectUtils.isNotEmpty(sysRouteMatch.getMatchHosts()) && !"[]".equals(sysRouteMatch.getMatchHosts())){
                match.put("Hosts",JSONArray.parse(sysRouteMatch.getMatchHosts()));
            }

            if(ObjectUtils.isNotEmpty(sysRouteMatch.getMatchMethods()) && !"[]".equals(sysRouteMatch.getMatchMethods())){
                match.put("Methods",JSONArray.parse(sysRouteMatch.getMatchMethods()));
            }

            if(ObjectUtils.isNotEmpty(sysRouteMatch.getMatchHeaders()) && !"[]".equals(sysRouteMatch.getMatchHeaders())){
                match.put("Headers",JSONArray.parse(sysRouteMatch.getMatchHeaders()));
            }

            if(ObjectUtils.isNotEmpty(sysRouteMatch.getMatchQueryParameters()) && !"[]".equals(sysRouteMatch.getMatchQueryParameters())){
                match.put("QueryParameters",JSONArray.parse(sysRouteMatch.getMatchQueryParameters()));
            }

            routes.put("Match",match);

            // 匹配规则
            if(ObjectUtils.isNotEmpty(sysRouteMatch.getMatchTransforms()) && !"[]".equals(sysRouteMatch.getMatchTransforms())){
                routes.put("Transforms",JSONArray.parse(sysRouteMatch.getMatchTransforms()));
            }


            /**
             * ========== Clusters ==========
             */
            HashMap<String, Object> clusters = new HashMap<>();

            LambdaQueryWrapper<SysClusters> lambdaClusters = new LambdaQueryWrapper<>();
            lambdaClusters.eq(SysClusters::getDelFlag, "0");
            lambdaClusters.eq(SysClusters::getClustersKey,sysRoute.getClustersKey());
            lambdaClusters.eq(SysClusters::getIsBlacklist,"0");
            SysClusters sysClusters = sysClustersMapper.selectOne(lambdaClusters);

            if(ObjectUtils.isEmpty(sysClusters)){
                continue;
            }

            clusters.put("ClusterId",sysRoute.getClustersKey());
            
            if(ObjectUtils.isNotEmpty(sysClusters.getLoadBalancingPolicy())){
                clusters.put("LoadBalancingPolicy",sysClusters.getLoadBalancingPolicy());
            }

            HashMap<String, Object> destinations = new HashMap<>();

            LambdaQueryWrapper<SysContainer> lambdaContainer = new LambdaQueryWrapper<>();
            lambdaContainer.eq(SysContainer::getDelFlag, "0");
            lambdaContainer.eq(SysContainer::getClustersKey,sysRoute.getClustersKey());
            lambdaContainer.eq(SysContainer::getIsBlacklist,"0");
            lambdaContainer.eq(SysContainer::getContainerState,"0");
            List<SysContainer> sysContainers = sysContainerMapper.selectList(lambdaContainer);

            if(ObjectUtils.isEmpty(sysContainers)){
                continue;
            }

            for (SysContainer sysContainer : sysContainers) {

                String containerIp = sysContainer.getContainerIp();
                String containerPort = sysContainer.getContainerPort();
                String Address = Constants.HTTP + containerIp + ":" + containerPort;
                String containerKey = sysContainer.getContainerKey();

                HashMap<String, Object> address = new HashMap<>();
                address.put("Address",Address);
                destinations.put(containerKey,address);
            }

            clusters.put("Destinations",destinations);

            routesList.add(routes);
            clustersList.add(clusters);
        }

        HashMap<String, Object> results = new HashMap<>();
        results.put("Routes",routesList);
        results.put("Clusters",clustersList);
        return results;
    }

    @Override
    public List<SysGateway> getList(SysGateway sysGateway) {

        LambdaQueryWrapper<SysGateway> lambda = new LambdaQueryWrapper<>();
        lambda.eq(SysGateway::getDelFlag, "0");
        if (ObjectUtils.isNotEmpty(sysGateway.getId())) {
            lambda.eq(SysGateway::getId,sysGateway.getId());
        }

        if (ObjectUtils.isNotEmpty(sysGateway.getGatewayAlias())) {
            lambda.eq(SysGateway::getGatewayAlias,sysGateway.getGatewayAlias());
        }

        if (ObjectUtils.isNotEmpty(sysGateway.getGatewayKey())) {
            lambda.like(SysGateway::getGatewayKey,sysGateway.getGatewayKey());
        }

        if (ObjectUtils.isNotEmpty(sysGateway.getGatewayLink())) {
            lambda.eq(SysGateway::getGatewayLink,sysGateway.getGatewayLink());
        }

        if (ObjectUtils.isNotEmpty(sysGateway.getIsBlacklist())) {
            lambda.eq(SysGateway::getIsBlacklist,sysGateway.getIsBlacklist());
        }

        List<SysGateway> sysGateways = sysGatewayMapper.selectList(lambda);

        return sysGateways;
    }

    @Override
    public int insertGateway(SysGateway sysGateway) {

        if(ObjectUtils.isEmpty(sysGateway.getCreateTime())){
            sysGateway.setCreateTime(DateUtils.getNowDate());
        }

        String gatewayLink = sysGateway.getGatewayLink();
        LambdaQueryWrapper<SysGateway> lambda = new LambdaQueryWrapper<>();
        lambda.eq(SysGateway::getDelFlag, "0");
        lambda.eq(SysGateway::getGatewayLink,gatewayLink);
        List<SysGateway> sysGateways = sysGatewayMapper.selectList(lambda);

        if(ObjectUtils.isNotEmpty(sysGateways)){
            throw new LunaException("此网关已存在");
        }

        return sysGatewayMapper.insert(sysGateway);
    }

    @Override
    public int updateGateway(SysGateway sysGateway) {
        if(ObjectUtils.isEmpty(sysGateway.getUpdateTime())){
            sysGateway.setUpdateTime(DateUtils.getNowDate());
        }
        return sysGatewayMapper.updateById(sysGateway);
    }

    @Override
    public int deleteGateway(Long id) {

        SysGateway sysGateway = sysGatewayMapper.selectById(id);

        sysGateway.setUpdateTime(DateUtils.getNowDate());
        sysGateway.setDelFlag("2");

        return sysGatewayMapper.updateById(sysGateway);
    }

    @Override
    public int refreshConfig(String gatewayKey) {

        String redisKey = RedisConstants.LUNA_GATEWAY_CONFIG + gatewayKey;

        if(redisCache.deleteObject(redisKey)){
            HashMap<String, Object> results = databaseAcquisitionConfig(gatewayKey);
            redisCache.setCacheObject(redisKey,results,24, TimeUnit.HOURS);
            return 1;
        }

        return -1;
    }
}
