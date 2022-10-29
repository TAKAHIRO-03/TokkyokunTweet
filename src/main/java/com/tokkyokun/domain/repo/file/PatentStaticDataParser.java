package com.tokkyokun.domain.repo.file;

import com.tokkyokun.domain.model.file.PatentStaticData;
import java.io.File;

public interface PatentStaticDataParser {

    PatentStaticData parse(File file);

}
