package com.tokkyokun.domain.service;

import com.tokkyokun.domain.model.db.TweetPatentData;
import com.tokkyokun.domain.model.db.TweetPatentDataImg;
import com.tokkyokun.domain.model.file.PatentStaticData;
import com.tokkyokun.domain.repo.db.TweetPatentDataImgRepository;
import com.tokkyokun.domain.repo.db.TweetPatentDataRepository;
import com.tokkyokun.domain.repo.db.UpdSearchMstrRepository;
import com.tokkyokun.domain.repo.file.PatentStaticDataRepository;
import com.tokkyokun.domain.repo.rest.JPlatPatCiteDocInfoRepository;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterTweetPatentDataService {

    private final static long DELAY_TIME = 500;

    private final UpdSearchMstrRepository repo;

    private final JPlatPatCiteDocInfoRepository citeDocInfoRepo;

    private final TweetPatentDataRepository tweetRepo;

    private final TweetPatentDataImgRepository tweetImgRepo;

    private final PatentStaticDataRepository patentRepo;

    public void saveTweetPatentData() {
        final var twentyYearsAgo = this.generate20YearsAgo();
        final var docNums = this.repo.findDocNumByAppDtEquals(twentyYearsAgo);

        docNums.delaySubscription(Duration.ofMillis(DELAY_TIME)).subscribe(docNum -> {
            final var citeDocInfo = this.citeDocInfoRepo.fetchCiteDocInfoByDocNum(docNum);
            final Mono<PatentStaticData> patentData;
            try {
                patentData = this.patentRepo.findPatentData(docNum);
            } catch (final Exception e) {
                log.warn(e.getMessage());
                return;
            }
            final var tweetData = patentData.flatMap(patent ->
                citeDocInfo.map(cite -> {
                        final var numOfReferences = cite.result().getData().getPatentDoc()
                            .size();
                        return TweetPatentData.builder().applicant(patent.getApplicant())
                            .appDt(patent.getAppDt())
                            .inventTitle(patent.getInventTitle())
                            .numOfReferences(numOfReferences)
                            .summary(patent.getSummary())
                            .build();
                    }
                )
            );
            tweetData.subscribe(tweet -> {
                final var savedData = this.tweetRepo.save(tweet);
                savedData.subscribe(
                    sd -> patentData.subscribe(patent -> patent.getImages().forEach(p -> {
                        final var img = TweetPatentDataImg.builder()
                            .tweetPatentDataId(sd.getTweetPatentDataId())
                            .representativeDiagram(p).build();
                        this.tweetImgRepo.save(img);
                    })));
            });
        });

    }

    private Integer generate20YearsAgo() {
        final var format = new SimpleDateFormat("yyyyMMdd");
        final var date = new Date();
        final var dateAsStr = format.format(date.getTime());
        return Integer.valueOf(dateAsStr);
    }

}
