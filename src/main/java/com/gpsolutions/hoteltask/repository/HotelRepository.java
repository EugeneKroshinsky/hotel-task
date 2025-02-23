package com.gpsolutions.hoteltask.repository;


import com.gpsolutions.hoteltask.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    @Query("SELECT a.city, COUNT(h) FROM Address a LEFT JOIN Hotel h ON a.id = h.address.id GROUP BY a.city")
    List<Object[]> countHotelsByCity();

    @Query("SELECT a.country, COUNT(h) FROM Address a LEFT JOIN Hotel h ON a.id = h.address.id GROUP BY a.country")
    List<Object[]> countHotelsByCountry();


    @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand")
    List<Object[]> countHotelsByBrand();

    @Query("SELECT a.name, COUNT(h) FROM Amenity a LEFT JOIN a.hotels h GROUP BY a.name")
    List<Object[]> countHotelByAmenity();
}