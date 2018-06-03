package com.newbig.codetemplate.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
/**
 * User: Haibo
 * Date: 2018-05-01 10:01:11
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysRoleResourcesVo {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("orgId")
    private String orgId;
    @ApiModelProperty("gmtCreate")
    private Date gmtCreate;
    @ApiModelProperty("gmtModify")
    private Date gmtModify;
    @ApiModelProperty("creator")
    private String creator;
    @ApiModelProperty("modifier")
    private String modifier;
    @ApiModelProperty("isDeleted")
    private Boolean isDeleted;
    @ApiModelProperty("roleName")
    private String roleName;
    @ApiModelProperty("resourceIds")
    private String resourceIds;
}
