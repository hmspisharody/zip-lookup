package com.zip.ziplookup.http.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Place {

    @JsonProperty("place name")
    private String placeName;

    @JsonProperty("state abbreviation")
    private String stateAbbreviation;
}
