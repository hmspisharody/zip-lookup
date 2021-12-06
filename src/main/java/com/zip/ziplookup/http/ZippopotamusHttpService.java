package com.zip.ziplookup.http;

import com.zip.ziplookup.exception.ApiException;
import com.zip.ziplookup.http.response.ZippopotamusLocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.Arrays;

@Component
public class ZippopotamusHttpService {

    @Value("${zip.zippopotamus.host}")
    private String host;

    @Value("${zip.zippopotamus.path}")
    private String path;

    @Autowired
    private RestTemplate restTemplate;

    public ZippopotamusLocationResponse makeHttpCall(int zipCode) {
        String url = this.host + this.path + zipCode;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ZippopotamusLocationResponse zipResponse;

        try {
            ResponseEntity<ZippopotamusLocationResponse> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, ZippopotamusLocationResponse.class);

            zipResponse = response.getBody();
        } catch (HttpServerErrorException e) {
            throw new ApiException(e.getMessage(), e.getStatusCode().value());
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                throw new ApiException(String.format("No results found for zipCode %d", zipCode), HttpStatus.NOT_FOUND.value());
            }
            throw new ApiException(e.getMessage(), e.getStatusCode().value());
        } catch (UnknownHttpStatusCodeException e) {
            throw new ApiException(e.getMessage(), e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            throw new ApiException("Resource could not be reached", HttpStatus.BAD_GATEWAY.value());
        }

        return zipResponse;

    }
}
