package com.newbig.codetemplate.service.impl;

import com.newbig.codetemplate.service.CodeGenerator;
import com.newbig.codetemplate.service.CodeGeneratorManager;
import com.newbig.codetemplate.util.StringUtils;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service层 代码生成器
 */
public class ServiceGenerator extends CodeGeneratorManager implements CodeGenerator {

	@Override
	public void genCode(String tableName, String modelName) {
		Configuration cfg = getFreemarkerConfiguration();
		String customMapping = "/";
		String modelNameUpperCamel = StringUtils.isNullOrEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
		
		Map<String, Object> data = getDataMapInit(modelName, modelNameUpperCamel);
		try {
			// 创建 Service 接口
			File serviceFile = new File(PROJECT_PATH +"/service/"+ JAVA_PATH + PACKAGE_PATH_SERVICE + customMapping
					+ modelNameUpperCamel + "Service.java");
			// 查看父级目录是否存在, 不存在则创建
			if (!serviceFile.getParentFile().exists()) {
				serviceFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(data, new FileWriter(serviceFile));
			logger.info(modelNameUpperCamel + "Service.java 生成成功!");
		} catch (Exception e) {
			throw new RuntimeException("Service 生成失败!", e);
		}
	}
	
	/**
	 * 预置页面所需数据
	 * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
	 * @param modelNameUpperCamel 首字为大写的实体类名
	 * @return
	 */
	private Map<String, Object> getDataMapInit(String modelName, String modelNameUpperCamel) {
		Map<String, Object> data = new HashMap<>();
		data.put("date", DATE);
		data.put("author", AUTHOR);
		data.put("modelNameUpperCamel", modelNameUpperCamel);
		data.put("modelNameLowerCamel", StringUtils.toLowerCaseFirstOne(modelNameUpperCamel));
		data.put("basePackage", BASE_PACKAGE);
		
		return data;
	}
}
