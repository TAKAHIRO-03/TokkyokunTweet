package com.tokkyokun.domain.repo.rest;

import com.tokkyokun.domain.model.rest.JPlatpatAppProgressSimple;
import reactor.core.publisher.Mono;

public interface JPlatpatAppProgressSimpleRepository {

    Mono<JPlatpatAppProgressSimple> fetchAppProgressSimpleByDocNum(String accessToken,
        Integer docNum);

}
