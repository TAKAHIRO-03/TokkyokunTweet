package com.tokkyokun.domain.repo.rest;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class JPlatpatAppProgressSimpleRepositoryImpl implements
    JPlatpatAppProgressSimpleRepository {

    @Override
    public Mono<String> fetchAppProgressSimpleByDocNum(final String accessToken,
        final Integer docNum) {
        return null;
    }
}
