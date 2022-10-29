package com.tokkyokun.domain.model.file;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public final class PatentStaticData {

    @NonNull
    @NotNull
    private final Integer appDt;

    @NonNull
    @NotNull
    private final String applicant;

    @NonNull
    @NotNull
    private final String inventTitle;

    @NonNull
    @NotNull
    private final String summary;

    @NonNull
    @NotNull
    private final List<CompletableFuture<byte[]>> images;

    @Builder.Default
    private List<byte[]> cachedImages = Collections.emptyList();

    PatentStaticData(@NonNull @NotNull final Integer appDt,
        @NonNull @NotNull final String applicant,
        @NonNull @NotNull final String inventTitle, @NonNull @NotNull final String summary,
        @NonNull @NotNull final List<CompletableFuture<byte[]>> images) {
        this.appDt = appDt;
        this.applicant = applicant;
        this.inventTitle = inventTitle;
        this.summary = summary;
        this.images = images;
    }

    public List<byte[]> getImages() {
        if (CollectionUtils.isEmpty(this.images)) {
            return Collections.emptyList();
        }
        if (!CollectionUtils.isEmpty(this.cachedImages)) {
            return this.cachedImages;
        }
        CompletableFuture
            .allOf(this.images.toArray(new CompletableFuture[0]))
            .join(); // wait

        this.cachedImages = this.images.stream().map(x -> {
            try {
                return x.get();
            } catch (final InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return this.cachedImages;
    }

}
