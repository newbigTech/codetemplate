package com.newbig.codetemplate.app.advice;

import com.newbig.codetemplate.common.exception.UserRemindException;
import com.newbig.codetemplate.common.serializer.AppDateEditor;
import com.newbig.codetemplate.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.newbig.codetemplate.vo.ResponseVo.failure;

@ControllerAdvice
@Slf4j
public class AppControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVo<String> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}",request.getRequestURI(), ex);
        response.setCharacterEncoding("UTF-8"); //避免乱码

        if (ex instanceof UserRemindException) {
            return failure(ex.getMessage());
        } else if (ex instanceof DuplicateKeyException) {
            Pattern pattern = Pattern.compile("Duplicate entry '(.*)' for key '(.*)'");
            Matcher matcher = pattern.matcher(ex.getCause().getMessage());
            if (matcher.matches()) {
                String key = matcher.group(2);
                String value = matcher.group(1);
                return failure(key.replace("_UNIQUE", "") + ":" + value + " 已存在，请修改后重新添加");
            } else {
                return failure("系统有重复数据");
            }
        } else if (ex instanceof DataIntegrityViolationException) {
            if (ex.getCause().getMessage().contains("Data too long for column")) {
                Pattern pattern = Pattern.compile(".*Data too long for column '(.*)' at row.*");
                Matcher matcher = pattern.matcher(ex.getCause().getMessage());
                if (matcher.matches()) {
                    String key = matcher.group(1);
                    return failure("字段[" + key + "]长度超出数据库限制");
                }
                return failure("某些字段长度超出数据库限制");
            } else if (ex.getCause().getMessage().contains("doesn't have a default value")) {
                Pattern pattern = Pattern.compile(".*Field '(.*)' doesn't have a default value.*");
                Matcher matcher = pattern.matcher(ex.getCause().getMessage());
                if (matcher.matches()) {
                    String key = matcher.group(1);
                    return failure("字段[" + key + "]没有默认值");
                }
                return failure("某些字段没有默认值");
            } else {
                return failure(ex.getCause().getMessage());
            }
        } else if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            return failure(bindingResult.getFieldErrors().get(0).getField() + bindingResult.getFieldErrors().get(0).getDefaultMessage());
            //只返回第一条
        } else if (ex instanceof MissingServletRequestParameterException) {
            return failure("参数缺失");
        } else if (ex instanceof BindException) {
            BindingResult bindingResult = ((BindException) ex).getBindingResult();
            return failure(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        } else {
            return failure("系统异常");
        }
    }


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        //对于需要转换为Date类型的属性，使用DateEditor进行处理
        webDataBinder.registerCustomEditor(Date.class, new AppDateEditor(true));
    }
}
