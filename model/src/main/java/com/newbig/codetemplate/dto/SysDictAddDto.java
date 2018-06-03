package com.newbig.codetemplate.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class SysDictAddDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;
    @ApiModelProperty("namespace")
    @NotEmpty(message = "namespace不能为空")
    private String namespace;
    @ApiModelProperty("key")
    @NotEmpty(message = "key不能为空")
    private String key;
    @ApiModelProperty("value")
    @NotEmpty(message = "value不能为空")
    private String value;
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

}
