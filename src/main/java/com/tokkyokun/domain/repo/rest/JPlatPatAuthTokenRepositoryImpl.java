package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatPatAuthToken;
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
public class JPlatPatAuthTokenRepositoryImpl implements JPlatPatAuthTokenRepository {

    private final WebClient client;

    private final EnvUtil env;

    private final static String AUTH_TOKEN_URI = "/auth/token";

    private final static String BASE_URL = "https://ip-data.jpo.go.jp";

    @Override
    public Mono<JPlatPatAuthToken> fetchAccessToken() {
        return this.client.post().uri(URI.create(BASE_URL + AUTH_TOKEN_URI))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(BodyInserters.fromFormData("grant_type", "password")
                .with("username", this.env.getJ_platpatUsername())
                .with("password", this.env.getJ_platpatUsername()))
            .retrieve()
            .bodyToMono(JPlatPatAuthToken.class);
    }

    @Override
    public Mono<JPlatPatAuthToken> fetchAccessTokenByRefreshToken(final String refreshToken) {
        return this.client.post().uri(URI.create(BASE_URL + AUTH_TOKEN_URI))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                .with("refresh_token", refreshToken))
            .retrieve()
            .bodyToMono(JPlatPatAuthToken.class);
    }
}
