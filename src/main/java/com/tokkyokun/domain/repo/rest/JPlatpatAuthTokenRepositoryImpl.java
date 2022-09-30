package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.AuthTokenResponse;
import com.tokkyokun.util.EnvUtil;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class JPlatpatAuthTokenRepositoryImpl implements JPlatpatAuthTokenRepository {

    private final WebClient client;

    private final EnvUtil env;

    private final static String AUTH_TOKEN_URI = "/auth/token";

    @Override
    public Mono<AuthTokenResponse> fetchAccessToken() {
        return this.client.post().uri(URI.create(BASE_URL + AUTH_TOKEN_URI))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(BodyInserters.fromFormData("grant_type", "password")
                .with("username", this.env.getJ_platpatUsername())
                .with("password", this.env.getJ_platpatUsername()))
            .retrieve()
            .bodyToMono(AuthTokenResponse.class);
    }

    @Override
    public Mono<AuthTokenResponse> fetchAccessTokenByRefreshToken(final String refreshToken) {
        return this.client.post().uri(URI.create(BASE_URL + AUTH_TOKEN_URI))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                .with("refresh_token", refreshToken))
            .retrieve()
            .bodyToMono(AuthTokenResponse.class);
    }
}
