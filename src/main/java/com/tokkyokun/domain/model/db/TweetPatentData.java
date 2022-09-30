package com.tokkyokun.domain.model.db;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table(value = "tweet_patent_data")
@Builder
public class TweetPatentData {

    @Column(value = "tweet_patent_data_id")
    @Id
    @NonNull
    @NotNull
    Long tweetPatentDataId;

    @Column(value = "applicant")
    @NonNull
    @NotNull
    String applicant;

    @Column(value = "app_dt")
    @NonNull
    @NotNull
    Integer appDt;

    @Column(value = "invent_title")
    @NonNull
    @NotNull
    String inventTitle;

    @Column(value = "summary")
    @NonNull
    @NotNull
    String summary;

    @Column(value = "is_tweeted")
    boolean isTweeted;

    @Column(value = "representative_diagram")
    @NonNull
    @NotNull
    Byte representativeDiagram;

    @Column(value = "created_at")
    Timestamp createdAt;

    @Column(value = "updated_at")
    Timestamp updatedAt;

}
