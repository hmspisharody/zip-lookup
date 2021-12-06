package com.zip.ziplookup.model.repository;

import com.zip.ziplookup.model.entity.LocationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

    LocationEntity findByZipCode(int zipCode);

}
