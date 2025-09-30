package com.example.netops.device;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(@Valid @RequestBody DeviceDTO dto) {
        // 先不接数据库，返回一个模拟结果
        return Map.of(
                "id", 1,
                "name", dto.name(),
                "ip", dto.ip()
        );
    }
}
