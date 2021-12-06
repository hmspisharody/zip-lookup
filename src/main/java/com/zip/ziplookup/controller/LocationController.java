package com.zip.ziplookup.controller;

import com.zip.ziplookup.model.response.LocationResponse;
import com.zip.ziplookup.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "Get city and state for a US zip-code")
    @GetMapping("/zipinfo/{zipCode}")
    public ResponseEntity<LocationResponse> getLocation (@PathVariable("zipCode") Integer zipCode) {
        return locationService.getLocation(zipCode);
    }
}
