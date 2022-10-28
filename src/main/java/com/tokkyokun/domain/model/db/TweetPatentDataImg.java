package com.tokkyokun.domain.model.db;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table(value = "tweet_patent_data_img")
@Builder
public class TweetPatentDataImg {

    @Column(value = "tweet_patent_data_id")
    @Id
    @NonNull
    @NotNull
    Long tweetPatentDataId;

    @Column(value = "representative_diagram")
    @NonNull
    @NotNull
    Byte representativeDiagram;

}
