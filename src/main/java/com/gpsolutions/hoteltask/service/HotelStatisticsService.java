package com.gpsolutions.hoteltask.service;

import java.util.Map;

public interface HotelStatisticsService {
    Map<String, Long> getHistogram(String param);
}
