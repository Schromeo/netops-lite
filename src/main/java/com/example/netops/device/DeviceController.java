package com.example.netops.device;

import com.example.netops.common.BizException;
import com.example.netops.common.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/devices")
@Validated // 让方法/查询参数上的校验注解生效
public class DeviceController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<Map<String, Object>> create(@Valid @RequestBody DeviceDTO dto) {
        // 模拟创建成功
        Map<String, Object> data = Map.of(
                "id", 1,
                "name", dto.name(),
                "ip", dto.ip()
        );
        return Result.ok("created", data);
    }

    @GetMapping("/{id}")
    public Result<Map<String, Object>> find(@PathVariable Integer id) {
        if (id == 404) {
            // 显式用 404
            throw new BizException("DEVICE_NOT_FOUND", "设备不存在或已删除", HttpStatus.NOT_FOUND);
        }
        return Result.ok(Map.of("id", id, "name", "demo-device", "ip", "10.0.0.1"));
    }

    // 新增：按 IP 查询（演示查询参数校验）
    @GetMapping("/search")
    public Result<Map<String, Object>> search(
            @RequestParam
            @Pattern(regexp = "^(?:\\d{1,3}\\.){3}\\d{1,3}$", message = "IP 格式不正确（形如 192.168.1.10）")
            String ip
    ) {
        // 这里只做演示，返回一个假数据
        return Result.ok(Map.of("matched", true, "ip", ip));
    }
}
