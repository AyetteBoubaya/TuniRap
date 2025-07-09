package com.TuniRap.catalogueManagement.feign;

import com.TuniRap.catalogueManagement.dto.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authservice", url = "http://localhost:8887/api/v1/auth")
public interface AuthClient {
    @GetMapping("/validate")
    ResponseEntity<UserInfoDTO> validateToken(@RequestHeader("Authorization") String token);


}
