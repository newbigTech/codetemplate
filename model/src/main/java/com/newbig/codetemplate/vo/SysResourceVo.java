package com.newbig.codetemplate.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
/**
 * User: Haibo
 * Date: 2018-05-01 10:03:14
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysResourceVo {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("uri")
    private String uri;
    @ApiModelProperty("icon")
    private String icon;
    @ApiModelProperty("level")
    private Integer level;
    @ApiModelProperty("ancesstorId")
    private Long ancesstorId;
    @ApiModelProperty("parentId")
    private Long parentId;
    @ApiModelProperty("type")
    private String type;
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
    @ApiModelProperty("apis")
    private String apis;
}
