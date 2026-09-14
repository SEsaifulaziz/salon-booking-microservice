package com.devsaif.payload.dto;

import lombok.Data;

@Data
public class KeycloakUserDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
