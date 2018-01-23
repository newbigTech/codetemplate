package com.newbig.codetemplate.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UnitModel {
    private String packageName;
    //待测类名
    private String className;
    //依赖的bean key: 变量名 value: 对应的类型
    private List<DependencyBean> dependencyBeans;
    private List<String> imports;
    private List<Method> methods;

}
