package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatPatAppProgressSimple;
import com.tokkyokun.util.EnvUtil;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class JPlatPatAppProgressSimpleRepository extends JPlatPatBaseRepository {

    private final static String APP_PROGRESS_SIMPLE_URI = "/api/patent/v1/app_progress_simple";

    public JPlatPatAppProgressSimpleRepository(final WebClient client, final EnvUtil env) {
        super(client, env);
    }

    public Mono<JPlatPatAppProgressSimple> fetchAppProgressSimpleByDocNum(
        @NonNull @NotNull final Integer docNum) {

        final var accessToken = getJPlatPatAuthToken();
        return accessToken.flatMap(x ->
            this.client.get().uri(BASE_URL + APP_PROGRESS_SIMPLE_URI + "/" + docNum)
                .headers(h -> h.setBearerAuth(x.accessToken()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JPlatPatAppProgressSimple.class)
        );

    }

}
