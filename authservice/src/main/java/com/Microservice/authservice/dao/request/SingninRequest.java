package com.Microservice.authservice.dao.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingninRequest {

    @Getter
    @Setter
    private String email;
    private String password;
}
