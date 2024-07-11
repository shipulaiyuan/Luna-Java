package com.virtual.luna.controller.publish;

import com.virtual.luna.common.base.domain.BaseController;
import com.virtual.luna.common.base.domain.CommonResult;
import com.virtual.luna.module.system.vo.RegisterBodyVo;
import com.virtual.luna.module.system.interfaces.IServerRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册 Server
 */
@Tag(name = "接收客户端注册接口")
@RestController
@RequestMapping
public class ServerRegisterController extends BaseController {

    @Resource
    private IServerRegisterService serverRegisterService;

    @PermitAll
    @Operation(summary = "接收客户端注册接口")
    @PostMapping("/server-register")
    public CommonResult<Integer> serverRegister(@RequestBody RegisterBodyVo registerBodyDto, HttpServletRequest request)  {

        return toAjax(serverRegisterService.serverRegister(registerBodyDto,request));

    }

}
