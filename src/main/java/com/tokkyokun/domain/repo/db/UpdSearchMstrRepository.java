package com.tokkyokun.domain.repo.db;

import com.tokkyokun.domain.model.db.UpdSearchMstr;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UpdSearchMstrRepository extends ReactiveCrudRepository<UpdSearchMstr, Long> {

    @Query("SELECT u.doc_num FROM upd_search_mstr u WHERE u.app_dt = :appDt")
    Flux<Integer> findDocNumByAppDtEquals(Integer appDt);

}
