package com.newbig.codetemplate.app.controller.admin;

import com.newbig.codetemplate.dal.model.SysUser;
import com.newbig.codetemplate.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {
    @Autowired
    private GreetingService greetingService;


    @RequestMapping("/greeting")
    public String greeting() {
        return greetingService.greet();
    }
    @RequestMapping("/userList")
    public List<SysUser> userList() {
        return greetingService.getSysUserList();
    }
}
