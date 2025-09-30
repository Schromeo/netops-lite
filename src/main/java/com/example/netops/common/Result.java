package com.example.netops.common;

public record Result<T>(String code, String message, T data) {
    public static <T> Result<T> ok(T data) { return new Result<>("OK", "success", data); }
    public static <T> Result<T> ok(String message, T data) { return new Result<>("OK", message, data); }
}
