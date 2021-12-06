package com.zip.ziplookup.service.mapper;

import com.zip.ziplookup.exception.ApiException;
import com.zip.ziplookup.http.response.Place;
import com.zip.ziplookup.http.response.ZippopotamusLocationResponse;
import com.zip.ziplookup.model.entity.LocationEntity;
import com.zip.ziplookup.model.response.LocationResponse;
import com.zip.ziplookup.model.response.Message;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class LocationMapper {

    public LocationResponse buildResponseFromEntity(LocationEntity locationEntity) {
        return LocationResponse.builder()
                .responseCode(HttpStatus.OK.value())
                .message(
                        Message.builder()
                                .city(locationEntity.getCity())
                                .state(locationEntity.getState())
                                .build()
                ).build();
    }

    public LocationEntity buildEntityFromZippopotamusResponse(int zipCode, ZippopotamusLocationResponse response) {

        Place place = response.getPlaces().stream()
                .findFirst()
                .orElseThrow( () -> new ApiException("No location found for the zip code provided", 404));

        return LocationEntity.builder()
                .state(place.getStateAbbreviation())
                .city(place.getPlaceName())
                .zipCode(zipCode)
                .build();
    }

}
