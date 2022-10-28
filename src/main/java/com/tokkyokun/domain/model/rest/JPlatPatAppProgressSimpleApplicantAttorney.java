package com.tokkyokun.domain.model.rest;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@Jacksonized
@Builder
@Value
public class JPlatPatAppProgressSimpleApplicantAttorney implements
    Comparable<JPlatPatAppProgressSimpleApplicantAttorney> {

    Long applicantAttorneyCd;

    Integer repeatNumber;

    String name;

    Integer applicantAttorneyClass;

    /**
     * ${@inheritDoc}
     */
    @Override
    public int compareTo(@NotNull final JPlatPatAppProgressSimpleApplicantAttorney o) {
        return Integer.compare(this.repeatNumber, o.getRepeatNumber());
    }
}
