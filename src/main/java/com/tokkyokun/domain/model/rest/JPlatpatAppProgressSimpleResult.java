package com.tokkyokun.domain.model.rest;


import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class JPlatpatAppProgressSimpleResult {

    int statusCode;

    String errorMessage;

    int remainAccessCount;

    JPlatpatAppProgressSimpleData data;

}
