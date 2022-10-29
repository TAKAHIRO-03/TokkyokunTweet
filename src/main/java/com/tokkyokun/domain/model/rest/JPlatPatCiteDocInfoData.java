package com.tokkyokun.domain.model.rest;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class JPlatPatCiteDocInfoData {

    Long applicationNumber;

    List<JPlatPatCiteDocInfoPatentDoc> patentDoc;

    List<Object> nonPatentDoc;

}
