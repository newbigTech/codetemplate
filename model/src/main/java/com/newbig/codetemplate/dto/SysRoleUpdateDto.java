package com.newbig.codetemplate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * User: haibo
 * Date: 2017/10/2 下午6:16
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysRoleUpdateDto {
    @ApiModelProperty(name = "id")
    @NotNull(message = "id不能为空")
    private Integer id;
    @ApiModelProperty(name = "角色名称")
    @NotEmpty(message = "角色名称不能为空")
    private String name;
    @ApiModelProperty(name = "所属公司")
    private Integer companyId;
    @ApiModelProperty(name = "该角色对应的资源id列表")
    @NotNull(message = "资源id列表不能为空")
    private Set<Integer> resourceIds;
    @ApiModelProperty(name = "该角色对应的公司id列表")
    @NotNull(message = "公司id列表不能为空")
    private Set<Integer> companyIds;
}
