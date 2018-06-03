package com.newbig.codetemplate.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * User: haibo
 * Date: 2017/10/2 下午6:16
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysRoleDeleteDto {
    @NotNull(message = "id不能为空")
    private Integer id;
}
