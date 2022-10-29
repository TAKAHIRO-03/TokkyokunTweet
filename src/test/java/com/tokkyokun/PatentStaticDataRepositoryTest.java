package com.tokkyokun;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class PatentStaticDataRepositoryTest {

    @Test
    public void ファイル探索() throws Exception {

        final var path = new StringBuilder();
        path.append("src");
        path.append(File.separator);
        path.append("test");
        path.append(File.separator);
        path.append("resources");
        path.append(File.separator);
        path.append("patent");

        final var dir = new File(path.toString());
        final var result = new HashSet<File>();
        recursive(dir, 2022006151, result);

        System.out.println(result);
    }

    private void recursive(final File dir, final Integer docNum, final Set<File> result) {
        final var files = dir.listFiles();
        if (Objects.isNull(files)) {
            return;
        }
        if (!CollectionUtils.isEmpty(result)) {
            return;
        }

        for (final File f : files) {
            if (f.isDirectory()) {
                recursive(f, docNum, result);
            } else if (f.isFile() && f.getName().startsWith(docNum.toString())) {
                result.add(f);
            }
        }

    }

}
