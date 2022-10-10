package com.tokkyokun.domain.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class JPlatpatAppProgressSimpleData {

    Long applicationNumber;

    String inventionTitle;

    List<JPlatpatAppProgressSimpleApplicantAttorney> applicantAttorney;

    Integer filingDate;

    Long publicationNumber;

    @JsonProperty("ADPublicationNumber")
    Long ADPublicationNumber;

    Long nationalPublicationNumber;

    @JsonProperty("ADNationalPublicationNumber")
    Long ADNationalPublicationNumber;

    Integer publicationDate;

    Integer registrationNumber;

    Integer registrationDate;

    String erasureIdentifier;

    Integer expireDate;

    Integer disappearanceDate;

    List<Object> bibliographyInformation;

}
