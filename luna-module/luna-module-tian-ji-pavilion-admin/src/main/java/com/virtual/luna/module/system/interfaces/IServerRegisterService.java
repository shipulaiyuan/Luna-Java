package com.virtual.luna.module.system.interfaces;

import com.virtual.luna.module.system.vo.RegisterBodyVo;
import jakarta.servlet.http.HttpServletRequest;

public interface IServerRegisterService {

    int serverRegister(RegisterBodyVo registerBodyDto, HttpServletRequest request);
}
