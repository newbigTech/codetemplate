package com.newbig.codetemplate.app.controller.admin.v1;

import com.newbig.codetemplate.common.annotation.NoAuth;
import com.newbig.codetemplate.common.utils.LogUtil;
import com.newbig.codetemplate.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: haibo
 * Date: 2018/1/17 下午1:49
 * Desc:
 */
@RestController
@Slf4j
public class UserAdminController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private DemoService demoService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @GetMapping(value = "/tttt")
    @NoAuth
    public ResponseVo getUser(){
        LogUtil.info("sdsd",System.currentTimeMillis()+"");
        log.info("{},{}",System.currentTimeMillis()+"","sdsd答复dfdfd");
        redisTemplate.opsForHash().put("aa","aaa","sdsd");
        log.info(redisTemplate.opsForHash().get("aa","aaa").toString());
        return ResponseVo.success("");
    }
}
