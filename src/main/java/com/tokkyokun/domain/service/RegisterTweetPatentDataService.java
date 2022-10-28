package com.tokkyokun.domain.service;

import com.tokkyokun.domain.model.db.TweetPatentData;
import com.tokkyokun.domain.model.db.TweetPatentDataImg;
import com.tokkyokun.domain.model.file.PatentData;
import com.tokkyokun.domain.model.rest.JPlatPatAppProgressSimpleApplicantAttorney;
import com.tokkyokun.domain.model.rest.JPlatPatAuthToken;
import com.tokkyokun.domain.repo.db.TweetPatentDataImgRepository;
import com.tokkyokun.domain.repo.db.TweetPatentDataRepository;
import com.tokkyokun.domain.repo.db.UpdSearchMstrRepository;
import com.tokkyokun.domain.repo.file.PatentDataRepository;
import com.tokkyokun.domain.repo.rest.JPlatPatAppProgressSimpleRepository;
import com.tokkyokun.domain.repo.rest.JPlatPatAuthTokenRepository;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterTweetPatentDataService {

    private final static long DELAY_TIME = 500;

    private final UpdSearchMstrRepository repo;

    private final JPlatPatAuthTokenRepository authRepo;

    private final JPlatPatAppProgressSimpleRepository appRepo;

    private final TweetPatentDataRepository tweetRepo;

    private final TweetPatentDataImgRepository tweetImgRepo;

    private final PatentDataRepository<PatentData> patentRepo;

    public void saveTweetPatentData() {
        final var twentyYearsAgo = this.generate20YearsAgo();
        final var docNums = this.repo.findDocNumByAppDtEquals(twentyYearsAgo);

        // Fetch authenticate information from J-PlatPat.
        final var authInfo = this.authRepo.fetchAccessToken().map(JPlatPatAuthToken::accessToken);
        docNums.delaySubscription(Duration.ofMillis(DELAY_TIME)).subscribe(docNum -> {
            authInfo.subscribe(token -> {
                final var jPlatRes = this.appRepo.fetchAppProgressSimpleByDocNum(token, docNum);
                final var patentData = this.patentRepo.fetchPatentData(docNum);
                final var tweetData = jPlatRes.map(jPlat -> {
                    //applicant concat with comma
                    final var applicant = jPlat.result().getData().getApplicantAttorney().stream()
                        .sorted().map(JPlatPatAppProgressSimpleApplicantAttorney::getName)
                        .collect(Collectors.joining(","));
                    return TweetPatentData.builder().applicant(applicant)
                        .appDt(jPlat.result().getData().getPublicationDate())
                        .inventTitle(jPlat.result().getData().getInventionTitle())
                        .summary(patentData.summary()).build();

                });
                tweetData.subscribe(tweet -> {
                    final var savedData = this.tweetRepo.save(tweet);
                    savedData.subscribe(s -> {
                        patentData.images().forEach(p -> {
                            final var img = TweetPatentDataImg.builder()
                                .tweetPatentDataId(s.getTweetPatentDataId())
                                .representativeDiagram(p).build();
                            this.tweetImgRepo.save(img);
                        });
                    });
                });
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
