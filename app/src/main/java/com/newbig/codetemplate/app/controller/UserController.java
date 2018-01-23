package com.newbig.codetemplate.app.controller;

import com.github.pagehelper.PageInfo;
import com.newbig.codetemplate.common.annotation.NoAuth;
import com.newbig.codetemplate.common.constant.AppConstant;
import com.newbig.codetemplate.dal.model.User;
import com.newbig.codetemplate.dto.UserAddDto;
import com.newbig.codetemplate.dto.UserDeleteDto;
import com.newbig.codetemplate.dto.UserUpdateDto;
import com.newbig.codetemplate.service.UserService;
import com.newbig.codetemplate.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * User: Haibo
 * Date: 2018-01-22 20:48:10
 * Desc:
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/user")
@Api(value = "user相关api.", tags = {"user"})
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    @NoAuth
    public ResponseVo<PageInfo<User>> getList(
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        log.info("{}{}{}{}");
        return ResponseVo.success(userService.getList(pageSize,pageNum));
    }

    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<User> getDetail(
        @RequestParam(required = false) Integer id ){
        return ResponseVo.success(userService.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody UserAddDto userAddDto){
        userService.addUser(userAddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody UserUpdateDto userUpdateDto){
        userService.updateUser(userUpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody UserDeleteDto userDeleteDto){
        userService.deleteUser(userDeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
