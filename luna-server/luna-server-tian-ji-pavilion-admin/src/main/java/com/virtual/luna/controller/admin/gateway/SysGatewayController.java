package com.virtual.luna.controller.admin.gateway;

import com.virtual.luna.common.base.domain.BaseController;
import com.virtual.luna.common.base.domain.CommonResult;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.common.base.utils.StringUtils;
import com.virtual.luna.infra.system.domain.SysGateway;
import com.virtual.luna.module.system.service.ISysGatewayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "网关管理")
@RestController
@RequestMapping("/gateway")
public class SysGatewayController extends BaseController {

    @Resource
    private ISysGatewayService sysGatewayService;

    /**
     * 查询网关列表
     * @return
     */
    @Operation(summary = "查询网关列表")
    @GetMapping("/getList")
    public CommonResult<List<SysGateway>> getList(SysGateway sysGateway)  {
        return CommonResult.success(sysGatewayService.getList(sysGateway));
    }

    /**
     * 新增网关
     * @return
     */
    @Operation(summary = "新增网关")
    @PostMapping("/insertGateway")
    public CommonResult<Integer> insertGateway(@RequestBody SysGateway sysGateway) {

        if(StringUtils.isEmpty(sysGateway.getGatewayLink())){
            throw new LunaException("网关域名不能为空");
        }

        return toAjax(sysGatewayService.insertGateway(sysGateway));
    }

    /**
     * 修改网关
     * @return
     */
    @Operation(summary = "修改网关")
    @PutMapping("/updateGateway")
    public CommonResult<Integer> updateGateway(@RequestBody SysGateway sysGateway) {

        if(ObjectUtils.isEmpty(sysGateway.getId())){
            throw new LunaException("主键id不能为空");
        }

        return toAjax(sysGatewayService.updateGateway(sysGateway));
    }

    /**
     * 删除网关
     * @return
     */
    @Operation(summary = "删除网关")
    @DeleteMapping("/deleteGateway/{id}")
    public CommonResult<Integer> deleteGateway(@PathVariable("id") Long id) {

        return toAjax(sysGatewayService.deleteGateway(id));
    }
}
