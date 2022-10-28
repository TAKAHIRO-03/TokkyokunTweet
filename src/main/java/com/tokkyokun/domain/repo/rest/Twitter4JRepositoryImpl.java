package com.tokkyokun.domain.repo.rest;

import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.v1.Status;
import twitter4j.v1.StatusUpdate;

/**
 * An interface to post using the Twitter API and Twitter4J.
 */
@Slf4j
@Repository
public class Twitter4JRepositoryImpl implements TwitterRepository<Status> {

    /**
     * Number of images you can post on Twitter.
     */
    private final static long MAX_NUM_OF_IMG_UPLOAD = 4;

    /**
     * {@inheritDoc}
     */
    @Override
    public Status update(@NonNull @NotNull final String tweetText) throws TwitterException {
        final var twitter = Twitter.getInstance();
        final var status = twitter.v1().tweets().updateStatus(tweetText);
        log.info("Successfully updated the status to [{}].", status.getText());

        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status update(@NonNull @NotNull final String tweetText,
        @NonNull @NotNull final List<File> medias) throws TwitterException {

        final List<File> fixedMedias;
        if (MAX_NUM_OF_IMG_UPLOAD < medias.size()) {
            fixedMedias = medias.subList(0, (int) MAX_NUM_OF_IMG_UPLOAD);
            log.warn("There are {} MEDIA files present. I will upload 0 to {} indexes",
                medias.size(), MAX_NUM_OF_IMG_UPLOAD - 1);
        } else {
            fixedMedias = medias;
        }

        final var twitter = Twitter.getInstance();
        final var uploadedMediaIds = fixedMedias.stream().mapToLong(m -> {
            try {
                final var uploadedMedia = twitter.v1().tweets().uploadMedia(m);
                return uploadedMedia.getMediaId();
            } catch (final TwitterException e) {
                throw new RuntimeException(e);
            }
        }).toArray();

        final var statusUpdate = StatusUpdate.of(tweetText).mediaIds(uploadedMediaIds);
        final var status = twitter.v1().tweets().updateStatus(statusUpdate);
        log.info("Successfully updated the status to [{}].", status.getText());

        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status update(@NonNull @NotNull final String tweetText,
        @NonNull @NotNull final Long inReplyToStatusId) throws TwitterException {

        final var twitter = Twitter.getInstance();
        final var statusUpdate = StatusUpdate.of(tweetText).inReplyToStatusId(inReplyToStatusId);
        final var status = twitter.v1().tweets().updateStatus(statusUpdate);
        log.info("Successfully updated the status to [{}].", status.getText());

        return status;
    }

    /**
     * init
     */
    @PostConstruct
    public void init() {
        if (!new ClassPathResource("twitter4j.properties").exists()) {
            throw new IllegalStateException(
                "You must need to put \"twitter4j.properties\" file on class path. ");
        }
    }

}
