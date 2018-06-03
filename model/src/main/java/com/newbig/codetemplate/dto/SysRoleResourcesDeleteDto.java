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
public class SysRoleResourcesDeleteDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;

}
