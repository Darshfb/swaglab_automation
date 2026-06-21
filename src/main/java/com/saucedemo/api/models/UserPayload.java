package com.saucedemo.api.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPayload {
    private String username;
    private String password;
    private String email;
    private String role;
}
