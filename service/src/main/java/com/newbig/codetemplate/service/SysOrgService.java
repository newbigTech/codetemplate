package com.newbig.codetemplate.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.codetemplate.common.exception.UserRemindException;
import com.newbig.codetemplate.common.utils.StringUtil;
import com.newbig.codetemplate.dal.mapper.SysOrgMapper;
import com.newbig.codetemplate.dal.model.SysOrg;
import com.newbig.codetemplate.dto.SysOrgAddDto;
import com.newbig.codetemplate.dto.SysOrgUpdateDto;
import com.newbig.codetemplate.common.utils.BeanCopyUtil;

import com.newbig.codetemplate.service.helper.OrgHelper;
import com.newbig.codetemplate.vo.SysOrgTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

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
        example.createCriteria()
                .andEqualTo("isDeleted",0)
                .andEqualTo("level",0);
        List<SysOrg> list = sysOrgMapper.selectByExample(example);
        PageSerializable ps = new PageSerializable(list);
        if(CollectionUtils.isEmpty(list)){
            return ps;
        }
        List<Integer> orgIds = list.stream().map(SysOrg::getId).collect(Collectors.toList());
        example.clear();
        example.createCriteria()
                .andEqualTo("isDeleted",0)
                .andIn("ancesstorId",orgIds);
        List<SysOrg> orgs = sysOrgMapper.selectByExample(example);
        List<SysOrg> orgTrees = OrgHelper.buildOrgTree(orgs);
        ps.setList(orgTrees);
        return ps;
    }

    /**
    *获取详情
    * @param id
    * @return
    */
    public SysOrg getDetailById(Integer id){
        return sysOrgMapper.selectByPrimaryKey(id);
    }

    /**
    * 添加
    * @param sysOrgAddDto
    */
    public void addSysOrg(SysOrgAddDto sysOrgAddDto){
        SysOrg sysOrg = new SysOrg();
        sysOrg.setName(sysOrgAddDto.getName());
        if(null != sysOrgAddDto.getParentId()){
            SysOrg parentOrg = getDetailById(sysOrgAddDto.getParentId());
            if(null == parentOrg){
                throw new UserRemindException("父节点不存在");
            }
            sysOrg.setParentId(parentOrg.getId());
            sysOrg.setAncesstorId(parentOrg.getAncesstorId());
            sysOrg.setParentIds(StringUtil.concat(parentOrg.getLevel() == 0?"":parentOrg.getParentIds(),parentOrg.getId(),","));
            sysOrg.setLevel(parentOrg.getLevel()+1);
        }else{
            sysOrg.setParentId(1);
            sysOrg.setAncesstorId(1);
            sysOrg.setParentIds("1,");
            sysOrg.setLevel(1);
        }
        sysOrgMapper.insertSelective(sysOrg);
    }
    /**
    * 更新
    * @param sysOrgUpdateDto
    */
    public void updateSysOrg(SysOrgUpdateDto sysOrgUpdateDto){
        SysOrg sysOrg = new SysOrg();
        sysOrg.setId(sysOrgUpdateDto.getId());
        sysOrg.setName(sysOrgUpdateDto.getName());
        if(null != sysOrgUpdateDto.getParentId()){
            SysOrg parentOrg = getDetailById(sysOrgUpdateDto.getParentId());
            if(null == parentOrg){
                throw new UserRemindException("父节点不存在");
            }
            sysOrg.setParentId(parentOrg.getId());
            sysOrg.setAncesstorId(parentOrg.getAncesstorId());
            sysOrg.setParentIds(StringUtil.concat(parentOrg.getLevel() == 0?"":parentOrg.getParentIds(),parentOrg.getId(),","));
            sysOrg.setLevel(parentOrg.getLevel()+1);
        }
        sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
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
