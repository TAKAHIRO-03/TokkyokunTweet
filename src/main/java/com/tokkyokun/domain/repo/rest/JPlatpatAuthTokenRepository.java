package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.AuthTokenResponse;
import reactor.core.publisher.Mono;

public interface JPlatpatAuthTokenRepository {

    String BASE_URL = "https://ip-data.jpo.go.jp";

    Mono<AuthTokenResponse> fetchAccessToken();

    Mono<AuthTokenResponse> fetchAccessTokenByRefreshToken(String refreshToken);


}
