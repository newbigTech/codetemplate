package com.newbig.codetemplate.service.impl;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.codetemplate.model.*;
import com.newbig.codetemplate.service.CodeGenerator;
import com.newbig.codetemplate.service.CodeGeneratorManager;
import com.newbig.codetemplate.util.StringUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * User: haibo
 * Date: 2018/1/22 上午11:39
 * Desc:
 */
@Slf4j
public class UnitTestGenerator extends CodeGeneratorManager implements CodeGenerator{
    private static Integer endlineNum;
    private static Map<String,String> globalVariableMap = Maps.newHashMap();
    private static Map<String,String> allVariableMap = Maps.newHashMap();
    private static List<Method> methodList = Lists.newArrayList();
    private static List<String> importsList = Lists.newArrayList();
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
        String serviceName = modelNameUpperCamel+"Service";
        String serviceTestFileName = modelNameUpperCamel+"ServiceTest.java";
        String javaSrcPath = "/service/src/main/java";
        String testSrcPath = "/service/src/test/java";
        String packagePath = File.separator+(BASE_PACKAGE+".service").replace(".",File.separator)+File.separator;
        String templatePath = PROJECT_PATH+"/generator/src/main/resources/template";
        //设置源码根目录
        String javaFilePath= PROJECT_PATH + javaSrcPath + packagePath + modelNameUpperCamel+"Service.java";
        //设置输出源码文件
        File outFile = new File(PROJECT_PATH + testSrcPath + packagePath + serviceTestFileName);
        if (outFile.isDirectory()) {
            throw new RuntimeException("请指定数据输出文件，而非目录：" + outFile.getPath());
        }
        try {
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            FileTemplateLoader[] fileLoaders = new FileTemplateLoader[1];
            fileLoaders[0] = new FileTemplateLoader(new File(templatePath));
            MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(fileLoaders);
            //加载模板所在目录
            cfg.setTemplateLoader(multiTemplateLoader);
            //设置默认编码
            cfg.setDefaultEncoding("UTF-8");
            cfg.setNumberFormat("###############");
            cfg.setBooleanFormat("true,false");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            //加载模板
            Template temp1 = cfg.getTemplate("test.ftl");
            UnitModel unitModel = getUnitTestModel(javaFilePath, serviceName);
            packagePath = packagePath.substring(1, packagePath.length()-1);
            unitModel.setPackageName(packagePath.replace("/", "."));
            Writer out;
            try (OutputStream fos = new FileOutputStream(outFile)) {
                out = new OutputStreamWriter(fos);
                //生成源码
                temp1.process(unitModel, out);
                fos.flush();
                fos.close();
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static UnitModel getUnitTestModel(String javaFilePath, String className) throws FileNotFoundException {
        UnitModel unitModel = new UnitModel();
        unitModel.setClassName(className);

        CompilationUnit compilationUnit = JavaParser.parse(new File(javaFilePath));
        compilationUnit.accept(new LineNumVisitor(), null);
        compilationUnit.accept(new ImportsVisitor(), null);
        compilationUnit.accept(new VariableVisitor(), null);
        compilationUnit.accept(new MethodVisitor(), null);
        List<DependencyBean> beans = Lists.newArrayList();
        for(Map.Entry<String,String> entry: globalVariableMap.entrySet()){
            DependencyBean dependencyBean = new DependencyBean();
            dependencyBean.setClassName(entry.getValue());
            dependencyBean.setVariableName(entry.getKey());
            beans.add(dependencyBean);
        }
        unitModel.setImports(importsList);
        unitModel.setDependencyBeans(beans);
        unitModel.setMethods(methodList);
        return unitModel;
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration c, Void arg) {
            String name = c.getName().getIdentifier();
            Method method = new Method();
            method.setReturnType(c.getType().asString());
            method.setMethodName(name);
            List<Param> params = Lists.newArrayList();
            for(Parameter p : c.getParameters()){
                Param param = new Param();
                param.setName(p.getNameAsString());
                param.setType(p.getType().asString());
                params.add(param);
            }
            method.setParamList(params);
            List<DependencyMethod> dependencyMethods = Lists.newArrayList();
            BlockStmt body = c.getBody().get();
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                        super.visit(n, arg);
                        String dependencyBean = n.toString().split("\\.")[0];
                        if(globalVariableMap.get(dependencyBean)!=null) {
                            String p = n.getParentNode().get().toString();
                            if(p.contains("=") && p.contains(".")&& p.contains("(")){
                                String ret = p.split("=")[0].trim();
                                DependencyMethod dependencyMethod = new DependencyMethod();
                                dependencyMethod.setVariableName(ret);
                                dependencyMethod.setServiceName(dependencyBean);
                                dependencyMethod.setMethodName(n.toString().split("\\.")[1].split("\\(")[0]);
                                dependencyMethod.setReturnType(allVariableMap.get(ret));
                                String[] ss1 = p.split("\\(");
                                p = p.replace(ss1[0],"").replace("(","").
                                        replace(")","");
                                if(org.apache.commons.lang3.StringUtils.isEmpty(p)){
                                    dependencyMethod.setParamSize(0);
                                }else {
                                    String[] ss2 = p.split(",");
                                    dependencyMethod.setParamSize(ss2.length);
                                }
                                dependencyMethods.add(dependencyMethod);
                            }
                        }
                    }
                }.visit(body, null);
            } catch (Exception e) {
                new RuntimeException(e);
            }
            method.setDependencyMethodList(dependencyMethods);
            methodList.add(method);
            super.visit(c, arg);
        }
    }

    private static class VariableVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(VariableDeclarator c, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            String name = c.getName().getIdentifier();
            if(c.getEnd().get().line < endlineNum && !c.getInitializer().isPresent()) {
                globalVariableMap.put(name, c.getType().asString());
//                log.info("{},{}",name,c.getType().asString());
            }
            allVariableMap.put(name, c.getType().asString());
            super.visit(c, arg);
        }
    }
    private static class LineNumVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration c, Void arg) {
            if(null == endlineNum){
                endlineNum = c.getBegin().get().line;
                log.info("method begin line num {}",endlineNum);
            }
            super.visit(c, arg);
        }
    }

    private static class ImportsVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(ImportDeclaration c, Void arg) {
            importsList.add(c.toString().replace("\n",""));
            super.visit(c, arg);
        }
    }
}
