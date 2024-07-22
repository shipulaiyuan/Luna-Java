package com.virtual.luna.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.virtual.luna.common.base.utils.BeanUtils;
import com.virtual.luna.framework.web.domin.PageResult;
import com.virtual.luna.common.base.exception.LunaException;
import com.virtual.luna.common.base.utils.DateUtils;
import com.virtual.luna.common.base.utils.SnowUtil;
import com.virtual.luna.infra.system.domain.SysConfig;
import com.virtual.luna.infra.system.mapper.SysConfigMapper;
import com.virtual.luna.module.system.service.ISysConfigService;
import com.virtual.luna.module.system.vo.SysConfigInsertVo;
import com.virtual.luna.module.system.vo.SysConfigQueryVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public SysConfig selectConfigByKey(String configKey) {
        LambdaQueryWrapper<SysConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysConfig::getConfigLabel,configKey);
        lambdaQueryWrapper.eq(SysConfig::getConfigTag,"1");
        lambdaQueryWrapper.eq(SysConfig::getDelFlag,"0");
        SysConfig sysConfig = sysConfigMapper.selectOne(lambdaQueryWrapper);
        return sysConfig;
    }

    @Override
    public PageResult<SysConfig> selectList(SysConfigQueryVo sysConfigVo) {
        LambdaQueryWrapper<SysConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(SysConfig::getDelFlag,"0");

        if(ObjectUtils.isEmpty(sysConfigVo.getPageNum())){
            sysConfigVo.setPageNum(1);
        }

        if(ObjectUtils.isEmpty(sysConfigVo.getPageSize())){
            sysConfigVo.setPageSize(10);
        }

        if (ObjectUtils.isNotEmpty(sysConfigVo.getId())) {
            lambdaQueryWrapper.eq(SysConfig::getId, sysConfigVo.getId());
        }

        if (ObjectUtils.isNotEmpty(sysConfigVo.getConfigAlias())) {
            lambdaQueryWrapper.eq(SysConfig::getConfigAlias, sysConfigVo.getConfigAlias());
        }

        if (ObjectUtils.isNotEmpty(sysConfigVo.getConfigTag())) {
            lambdaQueryWrapper.eq(SysConfig::getConfigTag, sysConfigVo.getConfigTag());
        }

        if (ObjectUtils.isNotEmpty(sysConfigVo.getConfigVersion())) {
            lambdaQueryWrapper.eq(SysConfig::getConfigVersion, sysConfigVo.getConfigVersion());
        }

        if (ObjectUtils.isNotEmpty(sysConfigVo.getConfigLabel())) {
            lambdaQueryWrapper.eq(SysConfig::getConfigLabel, sysConfigVo.getConfigLabel());
        }

        lambdaQueryWrapper.orderByDesc(SysConfig::getCreateTime);

        // 创建分页对象
        Page<SysConfig> page = new Page<>(sysConfigVo.getPageNum(), sysConfigVo.getPageSize());

        IPage<SysConfig> records = sysConfigMapper.selectPage(page, lambdaQueryWrapper);

        return new PageResult<SysConfig>(records.getRecords(), records.getTotal());

    }

    @Override
    public int insertSysConfig(SysConfigInsertVo sysConfigInsertVo) {

        String configLabel = sysConfigInsertVo.getConfigLabel();
        if(ObjectUtils.isEmpty(configLabel)){
            throw new LunaException("标签不能为空");
        }

        if(ObjectUtils.isNotEmpty(sysConfigInsertVo.getId())){

            SysConfig sysConfig1 = sysConfigMapper.selectById(sysConfigInsertVo.getId());

            if(sysConfig1.getConfigInfo().equals(sysConfigInsertVo.getConfigInfo())){

                SysConfig sysConfig = BeanUtils.toBean(sysConfigInsertVo, SysConfig.class);
                sysConfig.setUpdateTime(DateUtils.getNowDate());
                return sysConfigMapper.updateById(sysConfig);

            }else{
                sysConfigInsertVo.setId(null);
            }
        }

        SysConfig sysConfig = BeanUtils.toBean(sysConfigInsertVo, SysConfig.class);
        publishHandle(sysConfig);

        if (ObjectUtils.isEmpty(sysConfig.getConfigVersion())) {
            sysConfig.setConfigVersion(String.valueOf(SnowUtil.nextId()));
        }

        sysConfig.setCreateTime(DateUtils.getNowDate());
        return sysConfigMapper.insert(sysConfig);

    }

    /**
     * 如需发布，则需要将已发配置文件置为未发布
     * @param sysConfig
     */
    private void publishHandle(SysConfig sysConfig) {
        if("1".equals(sysConfig.getConfigTag())){

            SysConfig sysConfig1 = selectConfigByKey(sysConfig.getConfigLabel());

            if(ObjectUtils.isNotEmpty(sysConfig1)){
                sysConfig1.setConfigTag("0");
                sysConfig1.setUpdateTime(DateUtils.getNowDate());
                sysConfigMapper.updateById(sysConfig1);
            }
        }
    }

    @Override
    public int deleteSysConfig(Long id) {
        SysConfig sysConfig = sysConfigMapper.selectById(id);

        sysConfig.setUpdateTime(DateUtils.getNowDate());
        sysConfig.setDelFlag("2");

        return sysConfigMapper.updateById(sysConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSysConfig(SysConfigInsertVo sysConfigInsertVo) {

        String configLabel = sysConfigInsertVo.getConfigLabel();
        if(ObjectUtils.isEmpty(configLabel)){
            throw new LunaException("标签不能为空");
        }

        SysConfig sysConfig1 = selectConfigByKey(sysConfigInsertVo.getConfigLabel());

        if(ObjectUtils.isNotEmpty(sysConfig1)){
            sysConfig1.setConfigTag("0");
            sysConfig1.setUpdateTime(DateUtils.getNowDate());
            sysConfigMapper.updateById(sysConfig1);
        }

        SysConfig sysConfig = BeanUtils.toBean(sysConfigInsertVo, SysConfig.class);

        sysConfigMapper.updateById(sysConfig);

        return 1;
    }

    @Override
    public List<String> getLabelList() {

        List<SysConfig> sysConfigs = sysConfigMapper.selectList(new LambdaQueryWrapper<SysConfig>()
                .eq(SysConfig::getDelFlag, "0"));

        Map<String, List<String>> groupedConfigLabels = sysConfigs.stream()
                .collect(Collectors.groupingBy(
                        SysConfig::getConfigLabel,
                        Collectors.mapping(
                                SysConfig::getConfigLabel,
                                Collectors.toList()
                        )
                ));


        List<String> configLabels = new ArrayList<>(groupedConfigLabels.keySet());

        return configLabels;

    }

    @Override
    public PageResult<SysConfig> selectQueryParameters(String configLabel,String queryParameters, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(SysConfig::getDelFlag,"0");

        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;  // 默认第一页
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;  // 默认每页显示10条数据
        }

        if(ObjectUtils.isEmpty(configLabel)){
            throw new LunaException("标签不能为空");
        }

        lambdaQueryWrapper.eq(SysConfig::getConfigLabel,configLabel);

        lambdaQueryWrapper.and(wrapper ->
                wrapper.like(SysConfig::getConfigAlias, queryParameters)
                        .or()
                        .like(SysConfig::getConfigVersion, queryParameters)
                        .or()
                        .like(SysConfig::getRemark, queryParameters)
        );

        // 创建分页对象
        Page<SysConfig> page = new Page<>(pageNum, pageSize);

        IPage<SysConfig> records = sysConfigMapper.selectPage(page, lambdaQueryWrapper);

        return new PageResult<SysConfig>(records.getRecords(), records.getTotal());
    }


}
