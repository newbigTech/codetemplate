package com.newbig.codetemplate.service.impl;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.codetemplate.service.CodeGenerator;
import com.newbig.codetemplate.service.CodeGeneratorManager;
import com.newbig.codetemplate.util.StringUtils;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

/**
 * User: haibo
 * Date: 2018/1/22 上午11:39
 * Desc:
 */
public class VoAndDtoGenerator extends CodeGeneratorManager implements CodeGenerator{
    public static void main(String[] args){
        VoAndDtoGenerator voAndDtoGenerator = new VoAndDtoGenerator();
        voAndDtoGenerator.genCode("User",null);
    }
    private Map<String,String> mss = Maps.newLinkedHashMap();
    /**
     * 代码生成主要逻辑
     *
     * @param tableName 表名
     * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
     */
    @Override
    public void genCode(String tableName, String modelName) {
        Configuration cfg = getFreemarkerConfiguration();
        String modelNameUpperCamel = StringUtils.isNullOrEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
        String path=PROJECT_PATH +File.separator+"dal"+JAVA_PATH+File.separator+MODEL_PACKAGE.replace(".",File.separator)+File.separator;
        try {
            CompilationUnit compilationUnit = JavaParser.parse(
                    new File(path+tableName+".java"));
            compilationUnit.accept(new VariableVisitor(), null);
            Map<String,Object> data = Maps.newHashMap();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("basePackage", BASE_PACKAGE);
            data.put("fields",mss);
            try {
                // 创建 Service 接口
                File voFile = new File(PROJECT_PATH +"/model/"+ JAVA_PATH + File.separator+BASE_PACKAGE.replace(".",File.separator) + File.separator
                     +"vo"+ File.separator + modelNameUpperCamel + "Vo.java");
                // 查看父级目录是否存在, 不存在则创建
                if (!voFile.getParentFile().exists()) {
                    voFile.getParentFile().mkdirs();
                }
                cfg.getTemplate("vo.ftl").process(data, new FileWriter(voFile));
                logger.info(modelNameUpperCamel + "Vo.java 生成成功!");
                List<String> dtos = Lists.newArrayList("Add","Update","Delete");
                for(String s: dtos) {
                    data.put("type",s);
                    data.put("modelNameUpperCamel", modelNameUpperCamel+s);
                    File dtoFile = new File(PROJECT_PATH + "/model/" + JAVA_PATH + File.separator + BASE_PACKAGE.replace(".", File.separator) + File.separator
                            + "dto" + File.separator + modelNameUpperCamel+s + "Dto.java");
                    // 查看父级目录是否存在, 不存在则创建
                    if (!dtoFile.getParentFile().exists()) {
                        dtoFile.getParentFile().mkdirs();
                    }
                    cfg.getTemplate("dto.ftl").process(data, new FileWriter(dtoFile));
                    logger.info(modelNameUpperCamel +s+ "Dto.java 生成成功!");
                }
            } catch (Exception e) {
                throw new RuntimeException("Dto 生成失败!", e);
            }
//            for(Map.Entry<String,String> entry: mss.entrySet()){
//                System.out.println(entry.getKey() +" === "+entry.getValue());
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private class VariableVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(VariableDeclarator c, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            String name = c.getName().getIdentifier();
            mss.put(name,c.getType().asString());
            super.visit(c, arg);
        }
    }
}
