package com.example.netops.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ① 请求体(@RequestBody) + @Valid 的字段校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest req) {
        Map<String, String> errs = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errs.put(fe.getField(), fe.getDefaultMessage());
        }
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(), req.getRequestURI(),
                "INVALID_PARAM", "参数校验失败", errs
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // 400
    }

    // ② JSON 语法错误 / 类型不匹配（反序列化阶段）
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(HttpMessageNotReadableException ex,
                                                       HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(), req.getRequestURI(),
                "BAD_JSON", "请求体 JSON 解析失败", null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // 400
    }

    // ③ 业务异常（你在代码里手动 throw）
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResponse> handleBiz(BizException ex, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(), req.getRequestURI(),
                ex.getCode(), ex.getMessage(), null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // 先统一 400
    }

    // ④ 兜底（未预料到的异常）
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(), req.getRequestURI(),
                "INTERNAL_ERROR", "服务开小差了", null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body); // 500
    }
}
