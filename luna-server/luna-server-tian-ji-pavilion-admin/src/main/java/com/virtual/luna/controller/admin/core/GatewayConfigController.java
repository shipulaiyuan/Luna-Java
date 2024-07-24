package com.virtual.luna.controller.admin.core;

import com.virtual.luna.common.base.domain.BaseController;
import com.virtual.luna.common.base.domain.CommonResult;
import com.virtual.luna.framework.web.domin.PageResult;
import com.virtual.luna.infra.system.domain.SysConfig;
import com.virtual.luna.module.system.service.ISysGatewayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Tag(name = "GateWay 配置")
@RestController
@RequestMapping
public class GatewayConfigController extends BaseController {

    @Resource
    private ISysGatewayService sysGatewayService;

    /**
     * 获取 Yarp 网关配置文件
     * @return
     */
    @PermitAll
    @Operation(summary = "获取 Yarp 网关配置文件")
    @GetMapping("/getConfig/{gatewayKey}")
    @Parameter(name = "gatewayKey", description = "网关key", required = true, example = "api_ekwclass")
    public CommonResult<HashMap<String, Object>> getConfig(@PathVariable("gatewayKey")String gatewayKey)  {
        return CommonResult.success(sysGatewayService.getGatewayConfig(gatewayKey));
    }

    /**
     * 刷新网关配置
     * @return
     */
    @Operation(summary = "刷新网关配置")
    @GetMapping("/refreshConfig/{gatewayKey}")
    @Parameter(name = "gatewayKey", description = "网关key", required = true, example = "api_ekwclass")
    public CommonResult<Integer> refreshConfig(@PathVariable("gatewayKey")String gatewayKey)  {
        return toAjax(sysGatewayService.refreshConfig(gatewayKey));
    }

}
