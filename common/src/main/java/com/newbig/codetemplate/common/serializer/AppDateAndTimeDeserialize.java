package com.newbig.codetemplate.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 这个是用于POST中日期的解析
 */
public class AppDateAndTimeDeserialize extends JsonDeserializer<Date> {
    private static final List<String> formarts = Lists.newArrayList();

    static {
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
        formarts.add("HH:mm");
        formarts.add("HH:mm:ss");
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {

        }
        return date;
    }

    @Override
    public Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
            throws IOException {
        String value = paramJsonParser.getText().trim();
        if ("".equals(value)) {
            return null;
        }
        if (value.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(value, formarts.get(0));
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(value, formarts.get(1));
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(value, formarts.get(2));
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(value, formarts.get(3));
        } else if (value.matches("^\\d{1,2}:\\d{1,2}$")) {
            return parseDate(value, formarts.get(4));
        } else if (value.matches("^\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(value, formarts.get(5));
        } else {
            return null;
        }
    }
}
