package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatPatAppProgressSimple;
import reactor.core.publisher.Mono;

public interface JPlatPatAppProgressSimpleRepository {

    Mono<JPlatPatAppProgressSimple> fetchAppProgressSimpleByDocNum(String accessToken,
        Integer docNum);

}
