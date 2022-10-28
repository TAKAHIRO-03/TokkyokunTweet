package com.tokkyokun.domain.repo.rest;

import java.io.File;
import java.util.List;

/**
 * An interface to post using the Twitter API.
 *
 * @param <T> You must specify response type.
 */
public interface TwitterRepository<T> {

    /**
     * tweet
     *
     * @param tweetText tweet detail.
     * @return T specify type
     * @throws Exception If an error occurs during posting.
     */
    T update(String tweetText) throws Exception;

    /**
     * tweet with multiple media
     *
     * @param tweetText tweet detail.
     * @param medias    tweet medias.
     * @return T specify type
     * @throws Exception If an error occurs during posting.
     */
    T update(String tweetText, List<File> medias) throws Exception;

    /**
     * tweet in tweet
     *
     * @param tweetText tweet detail.
     * @param tweetId   tweet id.
     * @return T specify type
     * @throws Exception If an error occurs during posting.
     */
    T update(String tweetText, Long tweetId) throws Exception;

}
