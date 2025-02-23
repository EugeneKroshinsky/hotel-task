    package com.gpsolutions.hoteltask.config;

    import com.gpsolutions.hoteltask.api.dto.HotelCreateDtoRequest;
    import com.gpsolutions.hoteltask.api.dto.HotelDetailsDtoResponse;
    import com.gpsolutions.hoteltask.api.dto.HotelDtoResponse;
    import com.gpsolutions.hoteltask.entities.Address;
    import com.gpsolutions.hoteltask.entities.Amenity;
    import com.gpsolutions.hoteltask.entities.Hotel;
    import org.modelmapper.Converter;
    import org.modelmapper.ModelMapper;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import java.util.List;

    @Configuration
    public class ModelMapperConfig {
        @Bean
        public ModelMapper modelMapper() {
            ModelMapper modelMapper = new ModelMapper();
            addHotelAddressMapping(modelMapper);
            addHotelAmenityToStringMapping(modelMapper);
            addStringToAmenityMapping(modelMapper);
            addListStringToAmenityMapping(modelMapper);
            return modelMapper;
        }

        private void addStringToAmenityMapping(ModelMapper modelMapper) {
            modelMapper.typeMap(String.class, Amenity.class)
                    .addMappings(mapper -> mapper.map(str -> str, Amenity::setName));

        }

        private void addListStringToAmenityMapping(ModelMapper modelMapper) {
            modelMapper.typeMap(HotelCreateDtoRequest.class, Hotel.class)
                    .addMappings(mapper -> mapper.using(createStringListConverter(modelMapper))
                            .map(HotelCreateDtoRequest::getAmenities, Hotel::setAmenities));
        }

        private Converter<List<String>, List<Amenity>> createStringListConverter(ModelMapper modelMapper) {
            return ctx -> ctx.getSource().stream()
                    .map(str -> modelMapper.map(str, Amenity.class))
                    .toList();
        }

        private void addHotelAddressMapping(ModelMapper modelMapper) {
            modelMapper.typeMap(Hotel.class, HotelDtoResponse.class)
                    .addMappings(mapper -> mapper.using(createAddressConverter())
                            .map(Hotel::getAddress, HotelDtoResponse::setAddress));
        }

        private Converter<Address, String> createAddressConverter() {
            return context -> {
                Address address = context.getSource();
                return String.format("%s %s, %s, %s, %s",
                        address.getHouseNumber(),
                        address.getStreet(),
                        address.getCity(),
                        address.getPostCode(),
                        address.getCountry());
            };
        }

        private void addHotelAmenityToStringMapping(ModelMapper modelMapper) {
            modelMapper.typeMap(Hotel.class, HotelDetailsDtoResponse.class)
                    .addMappings(mapper ->
                            mapper.using(createStringsFromAmenitiesConverter())
                                    .map(Hotel::getAmenities, HotelDetailsDtoResponse::setAmenities));
        }

        private Converter<List<Amenity>, List<String>> createStringsFromAmenitiesConverter() {
            return ctx -> ctx.getSource().stream()
                    .map(Amenity::getName)
                    .toList();
        }
    }
