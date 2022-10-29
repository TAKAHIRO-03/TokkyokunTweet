package com.tokkyokun.domain.repo.file;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tokkyokun.domain.model.file.PatentStaticData;
import java.io.File;

public class PatentStaticXmlDataParserImpl implements PatentStaticDataParser {

    private static final XmlMapper MAPPER = new XmlMapper();

    @Override
    public PatentStaticData parse(final File file) {
        return null;
    }
}
