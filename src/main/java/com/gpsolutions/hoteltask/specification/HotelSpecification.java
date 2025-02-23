package com.gpsolutions.hoteltask.specification;

import com.gpsolutions.hoteltask.entities.Address;
import com.gpsolutions.hoteltask.entities.Amenity;
import com.gpsolutions.hoteltask.entities.Hotel;
import com.gpsolutions.hoteltask.exceptions.InvalidFilterParameterException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HotelSpecification {
    public static Specification<Hotel> filterByParams(Map<String, String> params, List<String> amenities) {
        return (root, query, criteriaBuilder) -> {
            Join<Hotel, Address> addressJoin = root.join("address", JoinType.LEFT);
            Join<Hotel, Amenity> amenitiesJoin = root.join("amenities", JoinType.INNER);
            List<Predicate> predicates = new ArrayList<>();

            if (params.containsKey("name")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), params.get("name").toLowerCase()));
            }

            if (params.containsKey("brand")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), params.get("brand").toLowerCase()));
            }

            if (params.containsKey("city")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(addressJoin.get("city")), params.get("city").toLowerCase()));
            }

            if (params.containsKey("county")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(addressJoin.get("county")), params.get("county").toLowerCase()));
            }

            if (amenities != null && !amenities.isEmpty()) {
                predicates.add(amenitiesJoin.get("name").in(amenities));
            }

            if (!params.isEmpty() && predicates.isEmpty()) {
                log.error("Invalid filter parameters provided: {}", params);
                throw new InvalidFilterParameterException("No valid filter parameters provided");
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

