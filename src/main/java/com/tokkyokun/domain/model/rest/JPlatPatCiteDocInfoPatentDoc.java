package com.tokkyokun.domain.model.rest;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class JPlatPatCiteDocInfoPatentDoc {

    Long draftDate;

    Integer citationType;

    String documentNumber;

}
