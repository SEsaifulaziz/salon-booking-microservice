package com.devsaif.payload.dto;

import lombok.Data;

@Data
public class CredentialDTO {
    private String type;
    private String value;
    private Boolean temporary;
}
