package com.gpsolutions.hoteltask.repository;

import com.gpsolutions.hoteltask.entities.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
      Optional<Amenity> findByName(String name);
}