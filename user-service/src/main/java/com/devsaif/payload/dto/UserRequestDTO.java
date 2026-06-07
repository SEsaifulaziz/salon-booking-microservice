package com.devsaif.payload.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequestDTO {

    private String username;
    private Boolean enabled;
    private String email;
    private String firstName;
    private String lastName;
    private List<CredentialDTO> credentials = new ArrayList<>();
}
