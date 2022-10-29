package com.tokkyokun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokkyokun.domain.model.rest.JPlatPatAppProgressSimple;
import com.tokkyokun.domain.model.rest.JPlatPatCiteDocInfo;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class SerializeTest {

    @Test
    public void JPlatPatAppProgressSimple_シリアライズ() throws Exception {

        final var mapper = new ObjectMapper();
        final var path =
            "src" + File.separator + "test" + File.separator + "resources" + File.separator
                + "app_progress_simple.json";
        final var content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);

        // convert JSON file to map
        final var result = mapper.readValue(content, JPlatPatAppProgressSimple.class);

        // print map entries
        System.out.println(result);

    }

    @Test
    public void JPlatPatCiteDocInfoPatentDoc_シリアライズ() throws Exception {

        final var mapper = new ObjectMapper();
        final var path =
            "src" + File.separator + "test" + File.separator + "resources" + File.separator
                + "cite_doc_info.json";
        final var content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);

        // convert JSON file to map
        final var result = mapper.readValue(content, JPlatPatCiteDocInfo.class);

        // print map entries
        System.out.println(result);

    }

}