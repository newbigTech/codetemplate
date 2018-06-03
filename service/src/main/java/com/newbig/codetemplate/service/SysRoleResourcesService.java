package com.newbig.codetemplate.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.codetemplate.common.utils.BeanCopyUtil;
import com.newbig.codetemplate.dal.mapper.SysRoleResourcesMapper;
import com.newbig.codetemplate.dal.model.SysRoleResources;
import com.newbig.codetemplate.dto.SysRoleResourcesAddDto;
import com.newbig.codetemplate.dto.SysRoleResourcesUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * User: Haibo
 * Date: 2018-05-01 10:01:11
 * Desc:
 */
@Service
public class SysRoleResourcesService {

    @Autowired
    private SysRoleResourcesMapper sysRoleResourcesMapper;

    /**
     * 分页获取 sysRoleResources 列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysRoleResources> getList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(SysRoleResources.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        List<SysRoleResources> list = sysRoleResourcesMapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public SysRoleResources getDetailById(Integer id) {
        Example example = new Example(SysRoleResources.class);
        return sysRoleResourcesMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     *
     * @param sysRoleResourcesAddDto
     */
    public void addSysRoleResources(SysRoleResourcesAddDto sysRoleResourcesAddDto) {
        SysRoleResources sysRoleResources = new SysRoleResources();
        BeanCopyUtil.copyProperties(sysRoleResourcesAddDto, sysRoleResources);
        sysRoleResourcesMapper.insertSelective(sysRoleResources);
    }

    /**
     * 更新
     *
     * @param sysRoleResourcesUpdateDto
     */
    public void updateSysRoleResources(SysRoleResourcesUpdateDto sysRoleResourcesUpdateDto) {
        SysRoleResources sysRoleResources = new SysRoleResources();
        BeanCopyUtil.copyProperties(sysRoleResourcesUpdateDto, sysRoleResources);

        Example example = new Example(SysRoleResources.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        criteria.andEqualTo("id", sysRoleResourcesUpdateDto.getId());
        sysRoleResourcesMapper.updateByExample(sysRoleResources, example);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void deleteSysRoleResources(Integer id) {
        SysRoleResources sysRoleResources = new SysRoleResources();
        sysRoleResources.setIsDeleted(true);
        sysRoleResources.setId(id);
        sysRoleResourcesMapper.updateByPrimaryKeySelective(sysRoleResources);
    }
}
