package com.virtual.luna.controller.admin.gateway;

import com.virtual.luna.common.base.domain.BaseController;
import com.virtual.luna.common.base.domain.CommonResult;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.infra.system.domain.SysRoute;
import com.virtual.luna.infra.system.domain.SysRouteMatch;
import com.virtual.luna.module.system.vo.SysRouteVo;
import com.virtual.luna.module.system.service.ISysRouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "路由管理")
@RestController
@RequestMapping("/gateway/route")
public class SysRouteController extends BaseController {

    @Resource
    private ISysRouteService sysRouteService;

    /**
     * 查询路由列表
     * @return
     */
    @Operation(summary = "查询路由列表")
    @GetMapping("/getRoutes")
    public CommonResult<List<SysRouteVo>> getRoutes(SysRoute sysRoute)  {
        return CommonResult.success(sysRouteService.getRoutes(sysRoute));
    }

    /**
     * 新增路由
     * @return
     */
    @Operation(summary = "新增路由")
    @PostMapping("/insertRoute")
    public CommonResult<Integer> insertRoute(@RequestBody SysRouteVo sysRouteDto) {

        SysRoute sysRoute = sysRouteDto.getSysRoute();

        SysRouteMatch sysRouteMatch = sysRouteDto.getSysRouteMatch();


        if(ObjectUtils.isEmpty(sysRoute)){
            throw new LunaException("路由Object不能为空");
        }

        if(ObjectUtils.isEmpty(sysRouteMatch)){
            throw new LunaException("匹配Object不能为空");
        }

        if(ObjectUtils.isEmpty(sysRoute.getGatewayKey())){
            throw new LunaException("网关key不能为空");
        }

        if(ObjectUtils.isEmpty(sysRoute.getClustersKey())){
            throw new LunaException("集群key不能为空");
        }

        if(ObjectUtils.isEmpty(sysRouteMatch.getMatchPath())){
            throw new LunaException("路径匹配不能为空");
        }

        return toAjax(sysRouteService.insertRoute(sysRouteDto));
    }

    /**
     * 修改路由
     * @return
     */
    @Operation(summary = "修改路由")
    @PutMapping("/updateRoute")
    public CommonResult<Integer> updateRoute(@RequestBody SysRouteVo sysRouteDto) {

        SysRoute sysRoute = sysRouteDto.getSysRoute();

        SysRouteMatch sysRouteMatch = sysRouteDto.getSysRouteMatch();


        if(ObjectUtils.isEmpty(sysRoute)){
            throw new LunaException("路由Object不能为空");
        }

        if(ObjectUtils.isEmpty(sysRouteMatch)){
            throw new LunaException("匹配Object不能为空");
        }

        if(ObjectUtils.isEmpty(sysRoute.getId())){
            throw new LunaException("路由id不能为空");
        }

        if(ObjectUtils.isEmpty(sysRouteMatch.getId())){
            throw new LunaException("路径匹配id不能为空");
        }

        return toAjax(sysRouteService.updateRoute(sysRouteDto));
    }

    /**
     * 删除路由
     * @return
     */
    @Operation(summary = "删除路由")
    @DeleteMapping("/deleteRoute/{id}")
    public CommonResult<Integer> deleteRoute(@PathVariable("id") Long id) {

        return toAjax(sysRouteService.deleteRoute(id));
    }
}
