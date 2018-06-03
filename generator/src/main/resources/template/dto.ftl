package ${basePackage}.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
/**
 * User: ${author}
 * Date: ${date}
 * Desc:
 */
@Setter
@Getter
@ToString
public class ${modelNameUpperCamel}Dto {
<#if type == "Delete">
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private ${fields["id"]} id;
<#else>
<#list fields?keys as key>
    @ApiModelProperty("${key}")
    <#if fields[key] == "String" && type == "Add">
    @NotEmpty(message = "${key}不能为空")
    <#elseif type == "Add">
    @NotNull(message = "${key}不能为空")
    <#elseif key == "id" && type == "Update">
    @NotNull(message = "${key}不能为空")
    </#if>
    private ${fields[key]} ${key};
</#list>
</#if>

}
