package com.tokkyokun.domain.repo.db;

import com.tokkyokun.domain.model.db.TweetPatentData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface TweetPatentDataRepository extends ReactiveCrudRepository<TweetPatentData, Long> {

    @Override
    @Transactional
    Mono<TweetPatentData> save(TweetPatentData entity);

}
