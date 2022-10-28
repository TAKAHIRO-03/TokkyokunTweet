package com.tokkyokun.domain.model.rest;

public record JPlatPatAuthToken(String accessToken, Integer expiresIn, Integer refreshExpiresIn,
                                String refreshToken, String tokenType, String idToken,
                                Integer notBeforePolicy, String sessionState, String scope) {

}
