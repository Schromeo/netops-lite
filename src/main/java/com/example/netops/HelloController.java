package com.example.netops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of("ok", true, "service", "netops-lite");
    }
}
