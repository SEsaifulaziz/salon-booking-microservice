package com.devsaif.service;

import lombok.RequiredArgsConstructor;
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



}
