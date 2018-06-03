package com.newbig.codetemplate.common.utils;

public class StringUtil extends org.apache.commons.lang3.StringUtils {

    public static String concat(String... strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return sb.toString();
    }

}
