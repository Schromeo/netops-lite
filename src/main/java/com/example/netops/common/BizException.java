package com.example.netops.common;

public class BizException extends RuntimeException {
    private final String code;
    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() { return code; }
}
