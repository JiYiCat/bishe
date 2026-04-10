package com.yushilong.bishe.common;

import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;
    private final String message;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.msg());
        this.code = resultCode.code();
        this.message = resultCode.msg();
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.code();
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}