package ${basePackage}.vo;

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
public class ${modelNameUpperCamel}Vo {

<#list fields?keys as key>
    @ApiModelProperty("${key}")
    private ${fields[key]} ${key};
</#list>
}
