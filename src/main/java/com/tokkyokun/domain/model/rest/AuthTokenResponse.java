package com.tokkyokun.domain.model.rest;

import lombok.Value;

@Value
public class AuthTokenResponse {

    String accessToken;

    Integer expiresIn;

    Integer refreshExpiresIn;

    String refreshToken;

    String tokenType;

    String idToken;

    Integer notBeforePolicy;

    String sessionState;

    String scope;

}
