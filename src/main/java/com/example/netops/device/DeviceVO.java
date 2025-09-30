package com.example.netops.device;

import java.time.LocalDateTime;

public record DeviceVO(
        Long id,
        String name,
        String ip,
        String status,
        LocalDateTime lastSeen
) {}
