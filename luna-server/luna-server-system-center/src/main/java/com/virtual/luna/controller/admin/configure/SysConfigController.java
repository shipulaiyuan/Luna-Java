package com.virtual.luna.controller.admin.configure;

import com.virtual.luna.common.base.domain.BaseController;
import com.virtual.luna.common.base.domain.CommonResult;
import com.virtual.luna.framework.web.domin.PageResult;
import com.virtual.luna.infra.system.domain.SysConfig;
import com.virtual.luna.module.system.service.ISysConfigService;
import com.virtual.luna.module.system.vo.SysConfigInsertVo;
import com.virtual.luna.module.system.vo.SysConfigQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin-配置管理")
@RestController
@RequestMapping("/server-config")
public class SysConfigController extends BaseController {

    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 服务获取配置
     * @param configKey
     * @return
     */
    @PermitAll
    @GetMapping("/{configKey}")
    @Operation(summary = "通过 configKey 获取配置")
    @Parameter(name = "configKey", description = "配置key", required = true, example = "yd-system-center")
    public String selectConfigByKey(@PathVariable("configKey") String configKey)  {
        return sysConfigService.selectConfigByKey(configKey).getConfigInfo();
    }

    /**
     * 配置列表
     * @param sysConfigVo
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "获取配置列表")
    public CommonResult<PageResult<SysConfig>> list(SysConfigQueryVo sysConfigVo)  {
        return CommonResult.success(sysConfigService.selectList(sysConfigVo));
    }

    /**
     * 别名 和 版本查询
     * @return
     */
    @Operation(summary = "查询配置by别名or版本or备注")
    @GetMapping("/selectQueryParameters")
    @Parameter(name = "queryParameters", description = "别名or版本or备注", required = true, example = "系统配置")
    @Parameter(name = "configLabel", description = "配置key", required = true, example = "yd-system-center")
    @Parameter(name = "pageNum", description = "页码", required = true, example = "1")
    @Parameter(name = "pageSize", description = "每条页数", required = true, example = "10")
    public CommonResult<PageResult<SysConfig>> selectQueryParameters(@RequestParam String queryParameters,
                                            @RequestParam String configLabel,
                                            @RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize) {
        return CommonResult.success(sysConfigService.selectQueryParameters(configLabel, queryParameters, pageNum, pageSize));
    }

    /**
     * 查询标签列表
     * @return
     */
    @Operation(summary = "查询配置标签数组")
    @GetMapping("/getLabelList")
    public CommonResult<List<String>> getLabelList()  {
        return CommonResult.success(sysConfigService.getLabelList());
    }

    /**
     * 新增配置
     * @param sysConfigInsertVo
     * @return
     */
    @Operation(summary = "新增配置")
    @PostMapping("/insertSysConfig")
    public CommonResult<Integer> insertSysConfig(@RequestBody SysConfigInsertVo sysConfigInsertVo)  {
        return toAjax(sysConfigService.insertSysConfig(sysConfigInsertVo));
    }

    /**
     * 修改状态
     * @param sysConfigInsertVo
     * @return
     */
    @Operation(summary = "修改配置")
    @PutMapping("/updateSysConfig")
    public CommonResult<Integer> updateSysConfig(@RequestBody SysConfigInsertVo sysConfigInsertVo)  {
        return toAjax(sysConfigService.updateSysConfig(sysConfigInsertVo));
    }

    /**
     * 删除配置
     * @param id
     * @return
     */
    @Operation(summary = "删除配置")
    @DeleteMapping("/deleteSysConfig/{id}")
    @Parameter(name = "id", description = "主键自增id", required = true)
    public CommonResult<Integer> deleteSysConfig(@PathVariable("id") Long id){
        return toAjax(sysConfigService.deleteSysConfig(id));
    }
}
