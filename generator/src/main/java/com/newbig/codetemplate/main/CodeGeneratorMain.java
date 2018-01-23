package com.newbig.codetemplate.main;

import com.google.common.collect.Lists;
import com.newbig.codetemplate.service.CodeGeneratorManager;

import java.util.List;

/**
 * 代码生成器启动项
 * */
public class CodeGeneratorMain {

	public static void main(String[] args) {
		List<String> tables = Lists.newArrayList(
				"user"
		);
		CodeGeneratorManager cgm = new CodeGeneratorManager();
		cgm.genCodeByTableName(tables);

	}
}
