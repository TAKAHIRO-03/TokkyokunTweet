package com.tokkyokun.domain.repo.rest;

import reactor.core.publisher.Mono;

public interface JPlatpatAppProgressSimpleRepository {

    String URL = "https://ip-data.jpo.go.jp";

    Mono<String> fetchAppProgressSimpleByDocNum(String accessToken, Integer docNum);

}
