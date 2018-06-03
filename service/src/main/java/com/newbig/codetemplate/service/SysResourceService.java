package com.newbig.codetemplate.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.codetemplate.common.utils.BeanCopyUtil;
import com.newbig.codetemplate.dal.mapper.SysResourceMapper;
import com.newbig.codetemplate.dal.model.SysResource;
import com.newbig.codetemplate.dto.SysResourceAddDto;
import com.newbig.codetemplate.dto.SysResourceUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * User: Haibo
 * Date: 2018-05-01 10:03:14
 * Desc:
 */
@Service
public class SysResourceService {

    @Autowired
    private SysResourceMapper sysResourceMapper;

    /**
     * 分页获取 sysResource 列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysResource> getList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(SysResource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        List<SysResource> list = sysResourceMapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public SysResource getDetailById(Integer id) {
        Example example = new Example(SysResource.class);
        return sysResourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     *
     * @param sysResourceAddDto
     */
    public void addSysResource(SysResourceAddDto sysResourceAddDto) {
        SysResource sysResource = new SysResource();
        BeanCopyUtil.copyProperties(sysResourceAddDto, sysResource);
        sysResourceMapper.insertSelective(sysResource);
    }

    /**
     * 更新
     *
     * @param sysResourceUpdateDto
     */
    public void updateSysResource(SysResourceUpdateDto sysResourceUpdateDto) {
        SysResource sysResource = new SysResource();
        BeanCopyUtil.copyProperties(sysResourceUpdateDto, sysResource);

        Example example = new Example(SysResource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        criteria.andEqualTo("id", sysResourceUpdateDto.getId());
        sysResourceMapper.updateByExample(sysResource, example);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void deleteSysResource(Long id) {
        SysResource sysResource = new SysResource();
        sysResource.setIsDeleted(true);
        sysResource.setId(id);
        sysResourceMapper.updateByPrimaryKeySelective(sysResource);
    }
}
