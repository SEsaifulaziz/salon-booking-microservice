package com.devsaif.service;

import com.devsaif.payload.response.dto.CredentialDTO;
import com.devsaif.payload.response.dto.SignupDTO;
import com.devsaif.payload.response.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
    private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL + "/admin/realms/master/users";

    private static final String TOKEN_URL = KEYCLOAK_BASE_URL + "realms/master/protocol/openid-connect/token";

    private static final String CLIENT_ID = "salon-booking-client";
    private static final String CLIENT_SECRET = "PnA7UBHoUmP5mK7u7UKhA2zaw8j7FL14";
    private static final String GRANT_TYPE = "password";
    private static final String scope = "openid profile email";
    private static final String username = "saifulaziz";
    private static final String password = "topik712";
    private static final String clientId  = "29047eb7-6229-4f3a-b614-3d6b6d1bdd49";

    private final RestTemplate restTemplate;


    public void createUser(SignupDTO signupDTO) throws Exception {

        String ACCESS_TOKEN = "";

        CredentialDTO credentialDTO = new CredentialDTO();
        credentialDTO.setTemporary(false);
        credentialDTO.setType("password");
        credentialDTO.setValue(signupDTO.getPassword());

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername(signupDTO.getUsername());
        userRequestDTO.setEmail(signupDTO.getEmail());
        userRequestDTO.setEnabled(true);
        userRequestDTO.setFirstName(signupDTO.getFirstName());
        userRequestDTO.setLastName(signupDTO.getLastName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ACCESS_TOKEN);

        HttpEntity<UserRequestDTO> requestDTOEntity = new HttpEntity<>(userRequestDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                KEYCLOAK_ADMIN_API,
                HttpMethod.POST,
                requestDTOEntity,
                String.class
        );

        if(response.getStatusCode() == HttpStatus.CREATED){
            System.out.println("user created successfully");
        }
    }

    public TokenResponse getAdminAccessToken(String username,
                                            String password,
                                            String grantType,q
                                            String refreshToken) {
        return null;
    }



}
