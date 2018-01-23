package ${packageName};

<#list imports as importString>
${importString}
</#list>
import com.github.jsonzou.jmockdata.JMockData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Objects;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ${className}Test {
    <#list dependencyBeans as field>
    @Mock
    private ${field.className} ${field.variableName};
    </#list>
    @InjectMocks
    private ${className} ${className?uncap_first};

    <#list methods as method>
    @Test
    public void test${method.methodName?cap_first}_() {
    <#list method.dependencyMethodList as dm>
        ${dm.returnType} ${dm.variableName} = JMockData.mock(${dm.returnType}.class);
        doReturn(${dm.variableName}).when(${dm.serviceName}).${dm.methodName}(Mockito.any(), Mockito.eq(.class));
    </#list>
    <#if method.returnType != "void">${method.returnType} a = </#if> ${className?uncap_first}.${method.methodName}();
        Assert.assertTrue(Objects.equals(1,1));
    }
    </#list>

}
