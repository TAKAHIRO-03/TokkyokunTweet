package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatPatCiteDocInfo;
import com.tokkyokun.util.EnvUtil;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class JPlatPatCiteDocInfoRepository extends JPlatPatBaseRepository {

    private final static String CITE_DOC_INFO_URI = "/api/patent/v1/cite_doc_info";

    public JPlatPatCiteDocInfoRepository(final WebClient client, final EnvUtil env) {
        super(client, env);
    }

    public Mono<JPlatPatCiteDocInfo> fetchCiteDocInfoByDocNum(
        @NonNull @NotNull final Integer docNum) {

        final var accessToken = getJPlatPatAuthToken();
        return accessToken.flatMap(x ->
            this.client.get().uri(BASE_URL + CITE_DOC_INFO_URI + "/" + docNum)
                .headers(h -> h.setBearerAuth(x.accessToken()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JPlatPatCiteDocInfo.class)
        );

    }


}
