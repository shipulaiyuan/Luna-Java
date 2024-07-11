package com.virtual.luna.controller.publish;

import com.virtual.luna.common.base.domain.BaseController;
import com.virtual.luna.module.system.service.ISysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin-配置管理")
@RestController
@RequestMapping("/server-config")
public class ServerConfigureController extends BaseController {

    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 服务获取配置
     *
     * @param configKey
     * @return
     */
    @PermitAll
    @GetMapping("/{configKey}")
    @Operation(summary = "通过 configKey 获取配置")
    @Parameter(name = "configKey", description = "配置key", required = true, example = "yd-system-center")
    public String selectConfigByKey(@PathVariable("configKey") String configKey) {
        return sysConfigService.selectConfigByKey(configKey).getConfigInfo();
    }
}
