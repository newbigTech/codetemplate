package com.newbig.codetemplate.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.codetemplate.dal.mapper.SysOrgMapper;
import com.newbig.codetemplate.dal.model.SysOrg;
import com.newbig.codetemplate.dto.SysOrgAddDto;
import com.newbig.codetemplate.dto.SysOrgUpdateDto;
import com.newbig.codetemplate.common.utils.BeanCopyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * User: Haibo
 * Date: 2018-06-03 15:50:00
 * Desc:
 */
@Service
public class SysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;

    /**
     * 分页获取 sysOrg 列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysOrg> getList(int pageSize, int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(SysOrg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        List<SysOrg> list = sysOrgMapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
    *获取详情
    * @param id
    * @return
    */
    public SysOrg getDetailById(Integer id){
        Example example = new Example(SysOrg.class);
        return sysOrgMapper.selectByPrimaryKey(id);
    }

    /**
    * 添加
    * @param sysOrgAddDto
    */
    public void addSysOrg(SysOrgAddDto sysOrgAddDto){
        SysOrg sysOrg = new SysOrg();
        BeanCopyUtil.copyProperties(sysOrgAddDto,sysOrg);
        sysOrgMapper.insertSelective(sysOrg);
    }
    /**
    * 更新
    * @param sysOrgUpdateDto
    */
    public void updateSysOrg(SysOrgUpdateDto sysOrgUpdateDto){
        SysOrg sysOrg = new SysOrg();
        BeanCopyUtil.copyProperties(sysOrgUpdateDto,sysOrg);

        Example example = new Example(SysOrg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        criteria.andEqualTo("id",sysOrgUpdateDto.getId());
        sysOrgMapper.updateByExample(sysOrg,example);
    }

    /**
    * 逻辑删除
    * @param id
    */
    public void deleteSysOrg(Integer id){
        SysOrg sysOrg = new SysOrg();
        sysOrg.setIsDeleted(true);
        sysOrg.setId(id);
        sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
    }
}
