package com.newbig.codetemplate.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@ApiModel(value = "角色列表")
public class SysRoleListVo {
    @ApiModelProperty(name = "id")
    private Integer id;
    @ApiModelProperty(name = "角色名称")
    private String name;
    @ApiModelProperty(name = "所属公司")
    private String companyId;
    @ApiModelProperty(name = "所属公司")
    private String companyName;
    @ApiModelProperty(name = "有权限的公司id列表")
    private String companyIds;
    @ApiModelProperty(name = "有权限的资源id列表")
    private String resourceIds;

    @ApiModelProperty(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
    @ApiModelProperty(name = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModify;
}
