package com.newbig.codetemplate.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Method {
    //方法名
    private String methodName;
    private String returnType;
    private List<Param>  paramList;
    private List<DependencyMethod> dependencyMethodList;
}
