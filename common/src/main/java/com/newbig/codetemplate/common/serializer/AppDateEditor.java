package com.newbig.codetemplate.common.serializer;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * 这个是用于GET中日期的解析
 */
@Slf4j
public class AppDateEditor extends PropertyEditorSupport {
    private static final List<String> formarts = Lists.newArrayList();

    static {
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
        formarts.add("yyyy-MM-ddTHH:mm");
        formarts.add("yyyy-MM-ddTHH:mm:ss");
    }

    private final boolean allowEmpty;
    private final int exactDateLength;

    public AppDateEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
        this.exactDateLength = -1;
    }

    @Override
    public void setAsText(String text) {
        if (this.allowEmpty && !org.springframework.util.StringUtils.hasText(text)) {
            this.setValue((Object) null);
        } else {
            if (text != null && this.exactDateLength >= 0) {
                throw new IllegalArgumentException("Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
            }
            this.setValue(this.convert(text));
        }

    }

//    public String getAsText() {
//        Date value = (Date) this.getValue();
//        DateFormat dateFormat = new SimpleDateFormat(format);
//        return value != null ? this.dateFormat.format(value) : "";
//    }

    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        return parseDate(source);
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @return Date 日期
     */
    public Date parseDate(String dateStr) {
        return new DateTime(dateStr.replace(" ","T")).toDate();
    }
}
