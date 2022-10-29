package com.tokkyokun.type;

import java.io.File;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public enum Extension {

    JPG("jpg"), XML("xml"), SGML("sgml");

    private final String word;

    Extension(final String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public static Extension getExtension(@NonNull @NotNull final File file)
        throws IllegalArgumentException {

        final var extensionAsAry = file.getName().split("\\.");
        final var extension = extensionAsAry[extensionAsAry.length - 1];

        for (final var e : values()) {
            if (e.getWord().equals(extension)) {
                return e;
            }
        }

        throw new IllegalArgumentException("File extension is not found. file=" + file);
    }

}
