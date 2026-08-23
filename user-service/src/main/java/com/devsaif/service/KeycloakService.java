package com.devsaif.service;

import com.devsaif.payload.dto.*;
import com.devsaif.payload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
    private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL + "/admin/realms/master/users";

    private static final String TOKEN_URL = KEYCLOAK_BASE_URL + "/realms/master/protocol/openid-connect/token";

    private static final String CLIENT_ID = "salon-booking-client";
    private static final String CLIENT_SECRET = "MgV4eA7v4AvGVIE1yMmSiwZYcLZOBZKNLmEezL1JsUKF95IaJdLHsgYZZoZl2VeEaMG9ZVPQQx5gtedRxiTdL8";
    private static final String GRANT_TYPE = "password";
    private static final String scope = "openid profile email";
    private static final String username = "saifulaziz";
    private static final String password = "topik712";
    private static final String clientId = "71e4b827-082b-4006-a4b2-667df1524da9";

    private final RestTemplate restTemplate;


    public void createUser(SignupDTO signupDTO) throws Exception {

        String ACCESS_TOKEN = getAdminAccessToken(
                username,
                password,
                GRANT_TYPE,
                null

        ).getAccessToken();

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
        userRequestDTO.getCredentials().add(credentialDTO);

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

        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("user created successfully");

            KeycloakUserDTO user = fetchFirstUserByName(signupDTO.getUsername(), ACCESS_TOKEN);

            KeycloakRole keycloakRole = getRoleByName(clientId,
                    ACCESS_TOKEN,
                    signupDTO.getRole().toString());

            List<KeycloakRole> roles = new ArrayList<>();
            roles.add(keycloakRole);

            assignRoleToUser(
                    user.getId(),
                    clientId,
                    roles,
                    ACCESS_TOKEN
            );
        } else {
            System.out.println("user creation failed");
            throw new Exception(response.getBody());
        }


    }

    public TokenResponse getAdminAccessToken(String username,
                                             String password,
                                             String grantType,
                                             String refreshToken) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("grant_type", grantType);
        requestBody.add("refresh_token", refreshToken);
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("scope", scope);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                requestEntity,
                TokenResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new Exception("Failed to obtain access token");
        }
    }

    public KeycloakRole getRoleByName(String clientId,
                                      String token,
                                      String role) {

        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/clients/" + clientId + "/roles/" + role;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setBearerAuth(token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<KeycloakRole> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                KeycloakRole.class
        );
        return response.getBody();
    }

    public KeycloakUserDTO fetchFirstUserByName(String username, String token) throws Exception {

        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<KeycloakUserDTO[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                KeycloakUserDTO[].class
        );
        KeycloakUserDTO[] users = response.getBody();
        if (users != null && users.length > 0) {
            return users[0];
        }
        throw new Exception("user not found with username" + username);

    }

    public void assignRoleToUser(
            String userId,
                                 String clientId,
                                 List<KeycloakRole> roles,
                                 String token
    ) throws Exception {

        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users/" + userId + "/role-mappings/clients/" + clientId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<KeycloakRole>> requestEntity = new HttpEntity<>(roles, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
        } catch (Exception ex) {
            throw new Exception("Failed to assign new role" + ex.getMessage());
        }

    }
}
