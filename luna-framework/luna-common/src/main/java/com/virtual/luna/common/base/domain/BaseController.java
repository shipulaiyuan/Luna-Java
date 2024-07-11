package com.virtual.luna.common.base.domain;

import com.virtual.luna.common.base.utils.StringUtils;
import org.apache.poi.ss.formula.functions.T;

/**
 * web层通用数据处理
 *
 * @author shi
 */
public class BaseController {

    /**
     * 响应返回结果
     */
    protected CommonResult<Integer> toAjax(Integer rows) {
        return rows > 0 ? CommonResult.success(rows) : CommonResult.error(rows);
    }



}
