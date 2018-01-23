package com.newbig.codetemplate.app;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static com.newbig.codetemplate.common.constant.AppConstant.PACKAGE_NAME;


@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = PACKAGE_NAME)
@MapperScan(basePackages = PACKAGE_NAME+".dal.mapper")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        log.info("--------------"+"---------------");
    }
}
