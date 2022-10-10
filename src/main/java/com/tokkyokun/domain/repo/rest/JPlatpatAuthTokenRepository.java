package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatpatAuthToken;
import reactor.core.publisher.Mono;

public interface JPlatpatAuthTokenRepository {

    Mono<JPlatpatAuthToken> fetchAccessToken();

    Mono<JPlatpatAuthToken> fetchAccessTokenByRefreshToken(String refreshToken);

}
