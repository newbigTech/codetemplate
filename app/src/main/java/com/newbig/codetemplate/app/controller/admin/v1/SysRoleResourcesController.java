package com.newbig.codetemplate.app.controller.admin.v1;

import com.github.pagehelper.PageSerializable;
import com.newbig.codetemplate.common.constant.AppConstant;
import com.newbig.codetemplate.dal.model.SysRoleResources;
import com.newbig.codetemplate.dto.SysRoleResourcesAddDto;
import com.newbig.codetemplate.dto.SysRoleResourcesDeleteDto;
import com.newbig.codetemplate.dto.SysRoleResourcesUpdateDto;
import com.newbig.codetemplate.service.SysRoleResourcesService;
import com.newbig.codetemplate.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * User: Haibo
 * Date: 2018-05-01 10:01:11
 * Desc:
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/sysRoleResources")
@Api(value = "sysRoleResources相关api.", tags = {"sysRoleResources"})
public class SysRoleResourcesController {
    @Resource
    private SysRoleResourcesService sysRoleResourcesService;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public ResponseVo<PageSerializable<SysRoleResources>> getList(
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(sysRoleResourcesService.getList(pageSize,pageNum));
    }

    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<SysRoleResources> getDetail(
        @RequestParam(required = false) Integer id ){
        return ResponseVo.success(sysRoleResourcesService.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody SysRoleResourcesAddDto sysRoleResourcesAddDto){
        sysRoleResourcesService.addSysRoleResources(sysRoleResourcesAddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody SysRoleResourcesUpdateDto sysRoleResourcesUpdateDto){
        sysRoleResourcesService.updateSysRoleResources(sysRoleResourcesUpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody SysRoleResourcesDeleteDto sysRoleResourcesDeleteDto){
        sysRoleResourcesService.deleteSysRoleResources(sysRoleResourcesDeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
