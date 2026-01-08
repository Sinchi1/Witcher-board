package org.kursach.service.dto;

import org.kursach.service.ValidationToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "auth-service",
        path = "/auth-service/auth"
)
public interface AuthService {
    @PostMapping("validate")
    TokenDTO validate(@RequestBody ValidationToken token);
}