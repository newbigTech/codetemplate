package ${basePackage}.app.controller;

import com.github.pagehelper.PageSerializable;
import ${basePackage}.common.constant.AppConstant;
import ${basePackage}.dal.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.vo.ResponseVo;
import ${basePackage}.dto.${modelNameUpperCamel}AddDto;
import ${basePackage}.dto.${modelNameUpperCamel}UpdateDto;
import ${basePackage}.dto.${modelNameUpperCamel}DeleteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * User: ${author}
 * Date: ${date}
 * Desc:
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/${modelNameLowerCamel}")
@Api(value = "${modelNameLowerCamel}相关api.", tags = {"${modelNameLowerCamel}"})
public class ${modelNameUpperCamel}Controller {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public ResponseVo<PageSerializable<${modelNameUpperCamel}>> getList(
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(${modelNameLowerCamel}Service.getList(pageSize,pageNum));
    }

    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<${modelNameUpperCamel}> getDetail(
        @RequestParam(required = false) Integer id ){
        return ResponseVo.success(${modelNameLowerCamel}Service.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody ${modelNameUpperCamel}AddDto ${modelNameLowerCamel}AddDto){
        ${modelNameLowerCamel}Service.add${modelNameUpperCamel}(${modelNameLowerCamel}AddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody ${modelNameUpperCamel}UpdateDto ${modelNameLowerCamel}UpdateDto){
        ${modelNameLowerCamel}Service.update${modelNameUpperCamel}(${modelNameLowerCamel}UpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody ${modelNameUpperCamel}DeleteDto ${modelNameLowerCamel}DeleteDto){
        ${modelNameLowerCamel}Service.delete${modelNameUpperCamel}(${modelNameLowerCamel}DeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
