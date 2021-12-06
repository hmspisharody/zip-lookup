package com.zip.ziplookup.service;

import com.zip.ziplookup.http.ZippopotamusHttpService;
import com.zip.ziplookup.http.response.ZippopotamusLocationResponse;
import com.zip.ziplookup.model.entity.LocationEntity;
import com.zip.ziplookup.model.repository.LocationRepository;
import com.zip.ziplookup.model.response.LocationResponse;
import com.zip.ziplookup.service.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final ZippopotamusHttpService zippopotamusHttpService;
    private final LocationRepository locationRepository;


    public ResponseEntity<LocationResponse> getLocation(Integer zipCode) {

        LocationEntity locationEntity = locationRepository.findByZipCode(zipCode);

        LocationResponse locationResponse;

        if (Objects.nonNull(locationEntity)) {
            log.info("Location for zipcode {} found in cache. {}", zipCode, locationEntity);
            locationResponse = LocationMapper.buildResponseFromEntity(locationEntity);
            log.info("location response from cache: {}", locationResponse);
        } else {
            log.info("Making http call to fetch location for zipCode: {}", zipCode);
            ZippopotamusLocationResponse zippopotamusLocationResponse = zippopotamusHttpService.makeHttpCall(zipCode);
            log.info("Location details fetched from Zippopotamus: {}. Writing to cache.", zippopotamusLocationResponse);
            locationEntity = LocationMapper.buildEntityFromZippopotamusResponse(zipCode, zippopotamusLocationResponse);
            locationRepository.save(locationEntity);
            locationResponse = LocationMapper.buildResponseFromEntity(locationEntity);
        }

        return new ResponseEntity(locationResponse, HttpStatus.OK);
    }
}
