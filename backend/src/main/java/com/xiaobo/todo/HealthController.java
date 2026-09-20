package com.xiaobo.todo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    public record Health(String status) {}

    @GetMapping
    public Health health() {
        return new Health("UP");
    }
}
