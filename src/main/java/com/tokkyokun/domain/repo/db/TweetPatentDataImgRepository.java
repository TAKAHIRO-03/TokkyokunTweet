package com.tokkyokun.domain.repo.db;

import com.tokkyokun.domain.model.db.TweetPatentDataImg;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface TweetPatentDataImgRepository extends
    ReactiveCrudRepository<TweetPatentDataImg, Long> {

    @Override
    @Transactional
    Mono<TweetPatentDataImg> save(TweetPatentDataImg entity);

}
