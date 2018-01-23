package com.newbig.codetemplate.main;

import com.newbig.codetemplate.service.impl.UnitTestGenerator;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UnitTestgen {


    /**指定模板名生成所需源码
     * */
    public static void main(String[] args ) {
        UnitTestGenerator cgm = new UnitTestGenerator();
        cgm.genCode("user",null);
    }


}
