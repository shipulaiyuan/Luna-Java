package com.virtual.luna.controller.admin.gateway;

import com.virtual.luna.common.base.domain.BaseController;
import com.virtual.luna.common.base.domain.CommonResult;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.infra.system.domain.SysClusters;
import com.virtual.luna.module.system.service.ISysClustersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "网关集群管理")
@RestController
@RequestMapping("/gateway/clusters")
public class SysClustersController extends BaseController {

    @Resource
    private ISysClustersService sysClustersService;

    /**
     * 查询集群列表
     * @return
     */
    @Operation(summary = "查询集群列表")
    @GetMapping("/getClusters")
    public CommonResult<List<SysClusters>> getClusters(SysClusters sysClusters)  {
        return CommonResult.success(sysClustersService.getClusters(sysClusters));
    }

    /**
     * 新增集群
     * @return
     */
    @Operation(summary = "新增集群")
    @PostMapping("/insterClusters")
    public CommonResult<Integer> insterClusters(@RequestBody SysClusters sysClusters)  {

        return toAjax(sysClustersService.insterClusters(sysClusters));
    }

    /**
     * 修改集群
     * @return
     */
    @Operation(summary = "修改集群")
    @PutMapping("/updateClusters")
    public CommonResult<Integer> updateClusters(@RequestBody SysClusters sysClusters)  {

        if(ObjectUtils.isEmpty(sysClusters.getId())){
            throw new LunaException("集群id不能为空");
        }

        return toAjax(sysClustersService.updateClusters(sysClusters));
    }

    /**
     * 删除集群
     * @return
     */
    @Operation(summary = "删除集群")
    @DeleteMapping("/deleteClusters/{id}")
    public CommonResult<Integer> deleteClusters(@PathVariable("id") Long id)  {

        return toAjax(sysClustersService.deleteClusters(id));
    }
}
