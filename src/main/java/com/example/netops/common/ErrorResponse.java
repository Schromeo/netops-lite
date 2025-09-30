package com.example.netops.common;

import java.time.LocalDateTime;
import java.util.Map;

// 统一错误返回结构
public record ErrorResponse(
        LocalDateTime timestamp,   // 出错时间
        String path,               // 请求路径
        String code,               // 错误码（稳定、可统计）
        String message,            // 可读提示
        Map<String, String> validationErrors // 字段级错误（可为 null）
) {}
