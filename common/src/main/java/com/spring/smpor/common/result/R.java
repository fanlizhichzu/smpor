package com.spring.smpor.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/7 16:37
 */
@Data
@ApiModel(value = "返回结果实体类", description = "结果实体类")
public class R implements Serializable {
    private static final long serialVersionUID = -8819983697240643575L;
    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    private R() {

    }

    public R(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    // 返回成功
    public static R success() {
        R result = new R();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    // 返回成功
    public static R success(Object data) {
        R result = new R();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    // 返回失败
    public static R fail(Integer code, String message) {
        R result = new R();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    // 返回失败
    public static R fail(ResultCode resultCode) {
        R result = new R();
        result.setResultCode(resultCode);
        return result;
    }
}
