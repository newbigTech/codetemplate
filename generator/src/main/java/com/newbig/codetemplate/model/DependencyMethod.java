package com.newbig.codetemplate.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DependencyMethod {
        private String serviceName;
        private String methodName;
        private Integer paramSize;
        private String returnType;
        private String variableName;
}
