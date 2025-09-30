package com.example.netops.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DeviceDTO(
        @NotBlank(message = "设备名不能为空")
        String name,

        @Pattern(
                regexp = "^(?:\\d{1,3}\\.){3}\\d{1,3}$",
                message = "IP 格式不正确（形如 192.168.1.10）"
        )
        String ip
) {}
