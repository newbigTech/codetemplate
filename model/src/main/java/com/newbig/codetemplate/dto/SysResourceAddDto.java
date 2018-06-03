package com.newbig.codetemplate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User: Haibo
 * Date: 2018-05-01 10:03:14
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysResourceAddDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;
    @ApiModelProperty("name")
    @NotEmpty(message = "name不能为空")
    private String name;
    @ApiModelProperty("uri")
    @NotEmpty(message = "uri不能为空")
    private String uri;
    @ApiModelProperty("icon")
    @NotEmpty(message = "icon不能为空")
    private String icon;
    @ApiModelProperty("level")
    @NotNull(message = "level不能为空")
    private Integer level;
    @ApiModelProperty("ancesstorId")
    @NotNull(message = "ancesstorId不能为空")
    private Long ancesstorId;
    @ApiModelProperty("parentId")
    @NotNull(message = "parentId不能为空")
    private Long parentId;
    @ApiModelProperty("type")
    @NotEmpty(message = "type不能为空")
    private String type;
    @ApiModelProperty("gmtCreate")
    @NotNull(message = "gmtCreate不能为空")
    private Date gmtCreate;
    @ApiModelProperty("gmtModify")
    @NotNull(message = "gmtModify不能为空")
    private Date gmtModify;
    @ApiModelProperty("creator")
    @NotEmpty(message = "creator不能为空")
    private String creator;
    @ApiModelProperty("modifier")
    @NotEmpty(message = "modifier不能为空")
    private String modifier;
    @ApiModelProperty("isDeleted")
    @NotNull(message = "isDeleted不能为空")
    private Boolean isDeleted;
    @ApiModelProperty("apis")
    @NotEmpty(message = "apis不能为空")
    private String apis;

}
