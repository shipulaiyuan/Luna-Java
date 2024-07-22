package com.virtual.luna.controller.admin.gateway;

import com.virtual.luna.common.base.domain.BaseController;
import com.virtual.luna.common.base.domain.CommonResult;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.common.base.utils.StringUtils;
import com.virtual.luna.infra.system.domain.SysContainer;
import com.virtual.luna.module.system.service.ISysContainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "容器管理")
@RestController
@RequestMapping("/gateway/container")
public class SysContainerController extends BaseController {

    @Resource
    private ISysContainerService sysContainerService;

    /**
     * 查询容器列表
     * @return
     */
    @Operation(summary = "查询容器列表")
    @GetMapping("/getContainers")
    public CommonResult<List<SysContainer>> getContainers(SysContainer sysContainer)  {
        return CommonResult.success(sysContainerService.getContainers(sysContainer));
    }

    /**
     * 新增容器
     * @return
     */
    @Operation(summary = "新增容器")
    @PostMapping("/insertContainer")
    public CommonResult<Integer> insertContainer(@RequestBody SysContainer sysContainer) throws Exception {

        if(StringUtils.isEmpty(sysContainer.getContainerIp())){
            throw new LunaException("容器ip不能为空");
        }

        if(StringUtils.isEmpty(sysContainer.getContainerIp())){
            throw new LunaException("容器端口号不能为空");
        }

        if(StringUtils.isEmpty(sysContainer.getClustersKey())){
            throw new LunaException("集群key不能为空");
        }

        return toAjax(sysContainerService.insertContainer(sysContainer));
    }

    /**
     * 修改容器
     * @return
     */
    @Operation(summary = "修改容器")
    @PutMapping("/updateContainer")
    public CommonResult<Integer> updateContainer(@RequestBody SysContainer sysContainer)  {

        if(ObjectUtils.isEmpty(sysContainer.getId())){
            throw new LunaException("容器id不能为空");
        }

        return toAjax(sysContainerService.updateContainer(sysContainer));
    }

    /**
     * 删除容器
     * @return
     */
    @Operation(summary = "删除容器")
    @DeleteMapping("/deleteContainer/{id}")
    public CommonResult<Integer> deleteContainer(@PathVariable("id") Long id)  {

        return toAjax(sysContainerService.deleteContainer(id));
    }
}
