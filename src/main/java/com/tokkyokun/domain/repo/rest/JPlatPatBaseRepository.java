package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatPatAuthToken;
import com.tokkyokun.util.EnvUtil;
import java.net.URI;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class JPlatPatBaseRepository {

    protected final static String BASE_URL = "https://ip-data.jpo.go.jp";

    protected final WebClient client;

    protected final EnvUtil env;

    private final static String AUTH_TOKEN_URI = "/auth/token";

    private static Mono<JPlatPatAuthToken> jPlatPatAuthToken;

    private static final Object lock = new Object();

    protected JPlatPatBaseRepository(final WebClient client, final EnvUtil env) {
        this.client = client;
        this.env = env;
    }

    protected Mono<JPlatPatAuthToken> fetchAccessTokenByRefreshToken(final String refreshToken) {
        return this.client.post().uri(URI.create(BASE_URL + AUTH_TOKEN_URI))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                .with("refresh_token", refreshToken))
            .retrieve()
            .bodyToMono(JPlatPatAuthToken.class);
    }

    protected Mono<JPlatPatAuthToken> getJPlatPatAuthToken() {
        synchronized (lock) {
            if (Objects.isNull(jPlatPatAuthToken)) {
                fetchAndSetJPlatPatAuthToken();
            }
            return jPlatPatAuthToken;
        }
    }

    private void fetchAndSetJPlatPatAuthToken() {
        jPlatPatAuthToken = this.client.post().uri(URI.create(BASE_URL + AUTH_TOKEN_URI))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .body(BodyInserters.fromFormData("grant_type", "password")
                .with("username", this.env.getJPlatPatUsername())
                .with("password", this.env.getJPlatPatPassword()))
            .retrieve()
            .bodyToMono(JPlatPatAuthToken.class);
    }

}
