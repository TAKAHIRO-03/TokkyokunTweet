package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatpatAppProgressSimple;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class JPlatpatAppProgressSimpleRepositoryImpl implements
    JPlatpatAppProgressSimpleRepository {

    private final static String BASE_URL = "https://ip-data.jpo.go.jp";

    private final static String APP_PROGRESS_SIMPLE_URI = "/api/patent/v1/app_progress_simple";

    private final WebClient client;

    @Override
    public Mono<JPlatpatAppProgressSimple> fetchAppProgressSimpleByDocNum(
        @NonNull @NotNull final String accessToken, @NonNull @NotNull final Integer docNum) {

        return this.client.get().uri(BASE_URL + APP_PROGRESS_SIMPLE_URI + "/" + docNum)
            .headers(h -> h.setBearerAuth(accessToken))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(JPlatpatAppProgressSimple.class);

    }
}
