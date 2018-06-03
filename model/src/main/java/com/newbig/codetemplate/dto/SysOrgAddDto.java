package com.newbig.codetemplate.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
/**
 * User: Haibo
 * Date: 2018-06-03 15:50:00
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysOrgAddDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;
    @ApiModelProperty("name")
    @NotEmpty(message = "name不能为空")
    private String name;
    @ApiModelProperty("parentId")
    @NotNull(message = "parentId不能为空")
    private Integer parentId;
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
    @ApiModelProperty("ancesstorId")
    @NotNull(message = "ancesstorId不能为空")
    private Integer ancesstorId;
    @ApiModelProperty("level")
    @NotNull(message = "level不能为空")
    private Integer level;
    @ApiModelProperty("parentIds")
    @NotEmpty(message = "parentIds不能为空")
    private String parentIds;

}
