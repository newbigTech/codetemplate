package com.newbig.codetemplate.app.controller.admin;

import com.newbig.codetemplate.common.annotation.NoAuth;
import com.newbig.codetemplate.common.constant.AppConstant;
import com.newbig.codetemplate.common.exception.UserRemindException;
import com.newbig.codetemplate.common.utils.JwtUtil;
import com.newbig.codetemplate.dal.model.SysUser;
import com.newbig.codetemplate.dto.LoginDto;
import com.newbig.codetemplate.service.SysUserService;
import com.newbig.codetemplate.vo.ResponseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * User: haibo
 * Date: 2018/1/17 下午3:30
 * Desc:
 */
@RestController
@RequestMapping(value = AppConstant.API_PREFIX_V1)
public class LoginController {
    @Autowired
    private SysUserService sysUserService;
    @PostMapping(value = "/doLogin")
    @ApiOperation(value = "1-1 用户登录", notes = " ",response = ResponseVo.class)
    @NoAuth
    public ResponseVo<Map<String,String>> doLogin(@Valid @RequestBody LoginDto loginDto) {
        SysUser user = sysUserService.getUserByMobile(loginDto.getMobile());
        if(null == user){
            throw new UserRemindException("用户不存在");
        }
        String token = JwtUtil.genToken(user.getMobile(),user.getUserId());
        return ResponseVo.success(token);
    }

    /**
     * Note: 由于用的是jwt token 有效时间为1天，token 也未存到Redis
     * 退出 后台不需要任何操作，但是如果要求用户只能在同一台设备登录则需要将
     * token存入 Redis 退出 也需要加权限验证，
     * 这里需要和前端一起 前端判断过期之后跳转到登录页面
     * @return
     */
    @PostMapping(value = "/doLogout")
    @ApiOperation(value = "1-2 用户退出", notes = " ",response = ResponseVo.class)
    @NoAuth
    public ResponseVo doLogout() {
//        String userId = JwtUtil.getUserUuid();
        return ResponseVo.success("退出成功");
    }

}
