package com.newbig.codetemplate.service.impl;

import com.newbig.codetemplate.service.CodeGenerator;
import com.newbig.codetemplate.service.CodeGeneratorManager;
import com.newbig.codetemplate.util.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Model & Mapper
 */
public class ModelAndMapperGenerator extends CodeGeneratorManager implements CodeGenerator {

	@Override
	public void genCode(String tableName, String modelName) {
		Context initConfig = initConfig(tableName, modelName);
		List<String> warnings = null;
		MyBatisGenerator generator = null;
		try {
			Configuration cfg = new Configuration();
			cfg.addContext(initConfig);
			cfg.validate();
			
			DefaultShellCallback callback = new DefaultShellCallback(true);
			warnings = new ArrayList<String>();
			generator = new MyBatisGenerator(cfg, callback, warnings);
			generator.generate(null);
		} catch (Exception e) {
			throw new RuntimeException("Model 和  Mapper 生成失败!", e);
		}
		
		if (generator == null || generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
			throw new RuntimeException("Model 和  Mapper 生成失败, warnings: " + warnings);
		}
		
		if (StringUtils.isNullOrEmpty(modelName)) {
			modelName = tableNameConvertUpperCamel(tableName);
		}
		
		logger.info("{}.java 生成成功!",modelName);
		logger.info( "{}Mapper.java 生成成功!",modelName);
		logger.info("{}Mapper.xml 生成成功!",modelName);
	}
	
	/**
	 * 完善初始化环境
	 * @param tableName 表名
	 * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
	 */
	private Context initConfig(String tableName, String modelName) {
		Context context = null;
		try {
			context = initMybatisGeneratorContext();
			JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
	        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH +"/dal/"+ JAVA_PATH);
	        javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
	        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
	        
	        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
	        javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH+"/dal/" + JAVA_PATH);
	        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
	        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
	        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
	        
	        TableConfiguration tableConfiguration = new TableConfiguration(context);
	        tableConfiguration.setTableName(tableName);
	        tableConfiguration.setDomainObjectName(modelName);
	        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
	        context.addTableConfiguration(tableConfiguration);
		} catch (Exception e) {
			throw new RuntimeException("ModelAndMapperGenerator 初始化环境异常!", e);
		}
		return context;
	}
}
