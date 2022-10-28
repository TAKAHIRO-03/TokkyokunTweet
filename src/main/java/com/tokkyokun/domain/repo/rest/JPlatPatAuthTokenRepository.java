package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatPatAuthToken;
import reactor.core.publisher.Mono;

public interface JPlatPatAuthTokenRepository {

    Mono<JPlatPatAuthToken> fetchAccessToken();

    Mono<JPlatPatAuthToken> fetchAccessTokenByRefreshToken(String refreshToken);

}
