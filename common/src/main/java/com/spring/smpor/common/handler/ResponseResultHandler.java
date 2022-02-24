package com.spring.smpor.common.handler;

import com.spring.smpor.common.annotation.ResponseResult;
import com.spring.smpor.common.exception.ErrorResult;
import com.spring.smpor.common.result.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 *
 * 使用 @ControllerAdvice & ResponseBodyAdvice
 * 拦截Controller方法默认返回参数，统一处理返回值/响应体
 *
 * @Auther: fanlz
 * @Date: 2021/12/8 9:27
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    // 标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    // 判断是否要执行 beforeBodyWrite 方法，true为执行，false不执行，有注解标记的时候处理返回值
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        // 判断请求是否有包装标记
        ResponseResult responseResultAnn = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);
        return responseResultAnn != null;
    }

    // 对返回值做包装处理
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ErrorResult) {
            ErrorResult error = (ErrorResult) body;
            return R.fail(error.getCode(), error.getMessage());
        } else if (body instanceof R) {
            return (R) body;
        } else if (body instanceof String) {
            return body;
        }
        return R.success(body);
    }
}
