package com.example.netops.device;

import com.example.netops.common.BizException;
import com.example.netops.common.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

@RestController
@RequestMapping("/api/devices")
@Validated
public class DeviceController {

    private final DeviceRepository repo;

    public DeviceController(DeviceRepository repo) {
        this.repo = repo;
    }

    // create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<DeviceVO> create(@Valid @RequestBody DeviceDTO dto) {
        repo.findByIp(dto.ip()).ifPresent(d -> {
            throw new BizException("DEVICE_IP_CONFLICT", "该 IP 已被占用", HttpStatus.CONFLICT);
        });

        Device dev = new Device();
        dev.setName(dto.name());
        dev.setIp(dto.ip());
        dev.setModel(null);
        if (dev.getStatus() == null) dev.setStatus("ONLINE");

        Device saved = repo.save(dev);

        return Result.ok("created", new DeviceVO(
                saved.getId(), saved.getName(), saved.getIp(), saved.getStatus(), saved.getLastSeen()
        ));
    }

    // find by id
    @GetMapping("/{id}")
    public Result<DeviceVO> find(@PathVariable @Min(1) Long id) {
        Device d = repo.findById(id).orElseThrow(() ->
                new BizException("DEVICE_NOT_FOUND", "设备不存在或已删除", HttpStatus.NOT_FOUND));

        return Result.ok(new DeviceVO(
                d.getId(), d.getName(), d.getIp(), d.getStatus(), d.getLastSeen()
        ));
    }

    // 分页/排序列表：/api/devices?page=0&size=10&sort=name,asc
    @GetMapping
    public Result<Page<Device>> list(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Device> p = repo.findAll(pageable);
        return Result.ok(p);
    }

    // 关键字搜索 + 分页：/api/devices/search?keyword=sw&page=0&size=5&sort=name,asc
    @GetMapping("/search")
    public Result<Page<Device>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort
    ) {
        String[] parts = sort.split(",");
        Sort s = (parts.length == 2 && "desc".equalsIgnoreCase(parts[1]))
                ? Sort.by(parts[0]).descending()
                : Sort.by(parts[0]).ascending();

        Pageable pageable = PageRequest.of(page, size, s);
        Page<Device> p = repo.findByNameContainingIgnoreCase(keyword, pageable);
        return Result.ok(p);
    }
}
