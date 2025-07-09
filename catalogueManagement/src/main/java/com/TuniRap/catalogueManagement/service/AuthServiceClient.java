package com.TuniRap.catalogueManagement.service;

import com.TuniRap.catalogueManagement.dto.UserInfoDTO;
import com.TuniRap.catalogueManagement.feign.AuthClient;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceClient {

    private final AuthClient authClient;

    public AuthServiceClient(AuthClient authClient) {
        this.authClient = authClient;
    }

    public UserInfoDTO validateToken(String token) {
        return authClient.validateToken(token).getBody();
    }

    public static boolean hasRole(UserInfoDTO user, String... roles) {
        if (user == null) return false;
        for (String role : roles) {
            if (user.role().equals(role)) return true;
        }
        return false;
    }

}

