package org.mics.core.exception;

import java.net.ConnectException;

import org.mics.core.response.SimpleResponse;
import org.mics.lang.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理增强处理
 * @author mics
 * @date 2020年6月17日
 * @version  1.0
 */
@RestControllerAdvice
public class CommonExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public SimpleResponse handleCommonException(CustomException e) {
        return new SimpleResponse(e.getCode(), e.getMessage());
    }

    /**
     * 处理所有不可知的异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    public SimpleResponse handleException(Exception e) {
        // 打印堆栈信息
        logger.error("发生异常", e);
        return new SimpleResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器繁忙，请稍候重试！");
    }

    /**
     * 处理所有接口数据验证异常
     * @param e 接口参数校验异常
     * @return 响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SimpleResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String[] str = e.getBindingResult()
                .getAllErrors().get(0).getCodes()[1].split("\\.");
        StringBuffer msg = new StringBuffer(str[1] + ":");
        msg.append(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new SimpleResponse(HttpStatus.BAD_REQUEST.value(), msg.toString());
    }

    /**
     * 处理所有接口数据绑定异常
     * @param e 接口参数绑定异常
     * @return 响应
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public SimpleResponse handleHttpMessageNotReadableException(Exception e) {
        String str = e.toString();
        String msg = str.substring(str.indexOf(':'));
        return new SimpleResponse(HttpStatus.BAD_REQUEST.value(), msg);
    }


    /**
     * ConnectException异常
     */
    @ExceptionHandler(ConnectException.class)
    public SimpleResponse handleConnectException(ConnectException e) {
        logger.error("网络链接异常", e);
        return new SimpleResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常，请稍后重试！");
    }
}
