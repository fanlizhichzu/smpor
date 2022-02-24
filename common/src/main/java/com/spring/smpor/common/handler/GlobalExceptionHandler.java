package com.spring.smpor.common.handler;

//import ch.qos.logback.classic.Logger;
import com.spring.smpor.common.exception.BizException;
import com.spring.smpor.common.exception.ErrorResult;
import com.spring.smpor.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/8 15:36
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public ErrorResult bizExceptionHandler(BizException e, HttpServletRequest request) {
        log.error("发生业务异常！原因是: {}", e.getMessage());
        return ErrorResult.fail(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handlerThrowable(Throwable e, HttpServletRequest request) {
        log.error("发生未知异常！原因是: ", e);
        return ErrorResult.fail(ResultCode.SYSTEM_ERROR, e);
    }

    // 参数校验异常
    @ExceptionHandler(BindException.class)
    public ErrorResult handleBindExcpetion(BindException e, HttpServletRequest request) {
        log.error("发生参数校验异常！原因是：",e);
        return ErrorResult.fail(ResultCode.PARAM_IS_INVALID, e, e.getAllErrors().get(0).getDefaultMessage());
    }

    // 参数校验异常
    @ExceptionHandler(FileUploadException.class)
    public ErrorResult handleBindExcpetion(FileUploadException e, HttpServletRequest request) {
        log.error("文件格式不正确！原因是：",e);
        return ErrorResult.fail(ResultCode.PARAM_IS_INVALID, e, e.getMessage());
    }
}
