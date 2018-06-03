package com.newbig.codetemplate.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.codetemplate.common.utils.BeanCopyUtil;
import com.newbig.codetemplate.dal.mapper.SysDictMapper;
import com.newbig.codetemplate.dal.model.SysDict;
import com.newbig.codetemplate.dto.SysDictAddDto;
import com.newbig.codetemplate.dto.SysDictUpdateDto;
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
public class SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     * 分页获取 sysDict 列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysDict> getList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        List<SysDict> list = sysDictMapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public SysDict getDetailById(Integer id) {
        Example example = new Example(SysDict.class);
        return sysDictMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     *
     * @param sysDictAddDto
     */
    public void addSysDict(SysDictAddDto sysDictAddDto) {
        SysDict sysDict = new SysDict();
        BeanCopyUtil.copyProperties(sysDictAddDto, sysDict);
        sysDictMapper.insertSelective(sysDict);
    }

    /**
     * 更新
     *
     * @param sysDictUpdateDto
     */
    public void updateSysDict(SysDictUpdateDto sysDictUpdateDto) {
        SysDict sysDict = new SysDict();
        BeanCopyUtil.copyProperties(sysDictUpdateDto, sysDict);

        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        criteria.andEqualTo("id", sysDictUpdateDto.getId());
        sysDictMapper.updateByExample(sysDict, example);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void deleteSysDict(Integer id) {
        SysDict sysDict = new SysDict();
        sysDict.setIsDeleted(true);
        sysDict.setId(id);
        sysDictMapper.updateByPrimaryKeySelective(sysDict);
    }
}
