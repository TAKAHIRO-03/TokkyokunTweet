package com.tokkyokun.domain.repo.file;

import static java.nio.file.Files.readAllBytes;

import com.tokkyokun.domain.model.file.PatentStaticData;
import com.tokkyokun.type.Extension;
import com.tokkyokun.util.EnvUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class PatentStaticDataRepository {

    private final EnvUtil env;

    private final Map<Extension, PatentStaticDataParser> parser;

    private final ExecutorService executor = Executors.newWorkStealingPool();

    public PatentStaticDataRepository(final EnvUtil env) {
        this.env = env;
        this.parser = Map.of(Extension.XML, new PatentStaticXmlDataParserImpl(), Extension.SGML,
            new PatentStaticSgmlDataParserImpl());
    }

    public Mono<PatentStaticData> findPatentData(final Integer docNum) throws Exception {
        final var dir = new File(this.env.getTokkyokunPatentDataBasePath());
        final var foundFiles = new HashSet<File>();
        this.recursiveSearchFile(dir, docNum, foundFiles);

        if (CollectionUtils.isEmpty(foundFiles)) {
            throw new Exception("patent data is not found. docNum=" + docNum);
        } else {
            log.info("Found files {}", foundFiles);
        }

        final var images = new ArrayList<CompletableFuture<byte[]>>();
        File foundFile = null;
        for (final var file : foundFiles) {
            if (file.toString().endsWith(Extension.JPG.getWord())) {
                images.add(
                    CompletableFuture.supplyAsync(() -> {
                        try {
                            return readAllBytes(file.toPath());
                        } catch (final IOException e) {
                            throw new RuntimeException(e);
                        }
                    }, this.executor));
            } else {
                if (Objects.isNull(foundFile)) {
                    foundFile = file;
                }
            }
        }
        if (Objects.isNull(foundFile)) {
            throw new Exception("patent data is not found. docNum=" + docNum);
        }

        final var extension = Extension.getExtension(foundFile);
        final var patentData = this.parser.get(extension).parse(foundFile);
        final var recreatedPatentData = PatentStaticData.builder()
            .appDt(patentData.getAppDt())
            .applicant(patentData.getApplicant())
            .inventTitle(patentData.getInventTitle())
            .summary(patentData.getSummary())
            .images(images)
            .build();

        return Mono.just(recreatedPatentData);
    }


    private void recursiveSearchFile(final File dir, final Integer docNum,
        final Set<File> foundFiles) {
        final var files = dir.listFiles();
        if (Objects.isNull(files)) {
            return;
        }
        if (!CollectionUtils.isEmpty(foundFiles)) {
            return;
        }

        for (final File f : files) {
            if (f.isDirectory()) {
                this.recursiveSearchFile(f, docNum, foundFiles);
            } else if (f.isFile() && f.getName().startsWith(docNum.toString())) {
                foundFiles.add(f);
            }
        }

    }

}
