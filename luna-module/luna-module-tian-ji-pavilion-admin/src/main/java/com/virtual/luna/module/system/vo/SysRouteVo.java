package com.virtual.luna.module.system.vo;

import com.virtual.luna.infra.system.domain.SysRoute;
import com.virtual.luna.infra.system.domain.SysRouteMatch;

public class SysRouteVo {

    private SysRoute sysRoute;

    private SysRouteMatch sysRouteMatch;

    public SysRoute getSysRoute() {
        return sysRoute;
    }

    public void setSysRoute(SysRoute sysRoute) {
        this.sysRoute = sysRoute;
    }

    public SysRouteMatch getSysRouteMatch() {
        return sysRouteMatch;
    }

    public void setSysRouteMatch(SysRouteMatch sysRouteMatch) {
        this.sysRouteMatch = sysRouteMatch;
    }
}
