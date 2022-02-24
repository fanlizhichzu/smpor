package com.spring.smpor.common.result;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/7 16:26
 */
public enum ResultCode {
    /*
        成功状态码
     */
    SUCCESS(20000, "成功"),
    SYSTEM_ERROR(10000, "系统异常，请稍后重试"),
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_NOT_SUPPOT(1002,"文件格式不正确");
    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
