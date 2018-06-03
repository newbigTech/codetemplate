## Spring Boot Project Generator

### 技术栈
* Spring Boot
* MyBatis Mapper
* Guava
* FastJSON
* Joda-Time 时间处理工具类
* Lombok    Setter/Getter
* ThymeLeaf 模板引擎
* Swagger   接口文档
* Mockito   单测Mock
* Powermock 单测Mock

### AOP
* 公共字段
* 权限管理
* 日志
### 前端
基于 https://github.com/PanJiaChen/vue-element-admin修改
### 生成项目模板
```
mvn clean install
mvn archetype:create-from-project
cd target/generated-sources/archetype
mvn clean install
cat ~/.m2/archetype-catalog.xml 
<archetype>
      <groupId>com.newbig.codetemplate</groupId>
      <artifactId>codetemplate-archetype</artifactId>
      <version>1.0-SNAPSHOT</version>
      <description>Parent pom providing dependency and plugin management for applications
                built with Maven</description>
</archetype>
```

### 使用项目模板
```
mvn archetype:generate -DarchetypeGroupId=com.newbig.codetemplate  -DarchetypeArtifactId=codetemplate-archetype -DarchetypeVersion=1.0-SNAPSHOT  -DgroupId=com.newbig.miniapp -DartifactId=miniapp -DarchetypeCatalog=internal

-DgroupId=com.newbig.miniapp    // 改成自己的
-DartifactId=miniapp            // 改成自己的

```