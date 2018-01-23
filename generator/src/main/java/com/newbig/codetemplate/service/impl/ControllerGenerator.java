package com.newbig.codetemplate.service.impl;

import com.google.common.base.CaseFormat;
import com.newbig.codetemplate.service.CodeGenerator;
import com.newbig.codetemplate.service.CodeGeneratorManager;
import com.newbig.codetemplate.util.StringUtils;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller层
 */
public class ControllerGenerator extends CodeGeneratorManager implements CodeGenerator {

	@Override
	public void genCode(String tableName, String modelName) {
		Configuration cfg = getFreemarkerConfiguration();
		String customMapping = "/" ;
		String modelNameUpperCamel = StringUtils.isNullOrEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
		
		Map<String, Object> data = getDataMapInit(tableName, modelName, modelNameUpperCamel);
		try {
			File controllerFile = new File(PROJECT_PATH+"/app/" + JAVA_PATH + PACKAGE_PATH_CONTROLLER+ customMapping
						 + modelNameUpperCamel + "Controller.java");
	        if (!controllerFile.getParentFile().exists()) {
	        	controllerFile.getParentFile().mkdirs();
	        }
	        cfg.getTemplate("controller.ftl").process(data, new FileWriter(controllerFile));
			logger.info( "{}Controller.java 生成成功!",modelNameUpperCamel);
		} catch (Exception e) {
			throw new RuntimeException("Controller 生成失败!", e);
		}
	}
	
	/**
	 * 预置页面所需数据
	 * @param tableName 表名
	 * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
	 * @param modelNameUpperCamel 首字为大写的实体类名
	 * @return
	 */
	private Map<String, Object> getDataMapInit(String tableName, String modelName, String modelNameUpperCamel) {
		Map<String, Object> data = new HashMap<>();
		data.put("date", DATE);
        data.put("author", AUTHOR);
        data.put("baseRequestMapping", StringUtils.toLowerCaseFirstOne(modelNameUpperCamel));
        data.put("modelNameUpperCamel", modelNameUpperCamel);
        data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
        data.put("basePackage", BASE_PACKAGE);
		
		return data;
	}
}
