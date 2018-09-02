package com.newbig.codetemplate.service;

import com.newbig.codetemplate.dal.mapper.SysUserMapper;
import com.newbig.codetemplate.dal.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreetingService {
    @Autowired
    private SysUserMapper sysUserMapper;
    public String greet() {
        return "Hello World";
    }
    public List<SysUser> getSysUserList(){
        return sysUserMapper.selectAll();
    }
}
