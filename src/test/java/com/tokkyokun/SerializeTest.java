package com.tokkyokun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokkyokun.domain.model.rest.JPlatpatAppProgressSimple;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class SerializeTest {

    @Test
    public void シリアライズ() throws Exception {

        final var mapper = new ObjectMapper();
        final var path =
            "src" + File.separator + "test" + File.separator + "resources" + File.separator
                + "app_progress_simple.json";
        final var content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);

        // convert JSON file to map
        final var result = mapper.readValue(content, JPlatpatAppProgressSimple.class);

        // print map entries
        System.out.println(result);

    }

}