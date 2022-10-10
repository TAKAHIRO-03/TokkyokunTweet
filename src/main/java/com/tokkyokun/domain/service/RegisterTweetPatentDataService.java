package com.tokkyokun.domain.service;

import com.tokkyokun.domain.model.db.TweetPatentData;
import com.tokkyokun.domain.model.rest.JPlatpatAppProgressSimpleApplicantAttorney;
import com.tokkyokun.domain.model.rest.JPlatpatAuthToken;
import com.tokkyokun.domain.repo.db.TweetPatentDataRepository;
import com.tokkyokun.domain.repo.db.UpdSearchMstrRepository;
import com.tokkyokun.domain.repo.rest.JPlatpatAppProgressSimpleRepository;
import com.tokkyokun.domain.repo.rest.JPlatpatAuthTokenRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterTweetPatentDataService {

    private final UpdSearchMstrRepository repo;

    private final JPlatpatAuthTokenRepository authRepo;

    private final JPlatpatAppProgressSimpleRepository appRepo;

    private final TweetPatentDataRepository tweetRepo;

    public void saveTweetPatentData() {
        final var authInfo = this.authRepo.fetchAccessToken()
            .map(JPlatpatAuthToken::accessToken);
        final var twentyYearsAgo = this.generate20YearsAgo();
        final var docNums = this.repo.findDocNumByAppDtEquals(twentyYearsAgo);

        docNums.subscribe(docNum -> {
            authInfo.subscribe(token -> {
                    final var res = this.appRepo.fetchAppProgressSimpleByDocNum(token, docNum);
                    final var tweetData = res.map(x -> {
                            /* concat with comma */
                            final var applicant = x.result().getData().getApplicantAttorney().stream()
                                .sorted()
                                .map(JPlatpatAppProgressSimpleApplicantAttorney::getName)
                                .collect(Collectors.joining(","));
                            return TweetPatentData.builder()
                                .applicant(applicant)
                                .appDt(x.result().getData().getPublicationDate())
                                .inventTitle(x.result().getData().getInventionTitle())
                                .summary("")
                                .representativeDiagram(null)
                                .build();

                        }
                    );
                    tweetData.subscribe(tweet -> this.tweetRepo.save(tweet));
                }
            );
        });

    }

    private Integer generate20YearsAgo() {
        final var format = new SimpleDateFormat("yyyyMMdd");
        final var date = new Date();
        final var dateAsStr = format.format(date.getTime());
        return Integer.valueOf(dateAsStr);
    }

}
