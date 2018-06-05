package com.company.service;

import com.company.model.Location;
import com.company.repository.CSVRepository;
import com.company.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private GoogleService googleService;

    @Autowired
    private CSVRepository csvRepository;

    public List<Location> findLocalTime(final String fileName) throws Exception {
        return findLocalTime(csvRepository.readLocation(fileName));
    }

    public List<Location> findLocalTime(final List<Location> locations) throws Exception {
        for (Location location : locations) {
            final String timeZoneId = googleService.getLocalTime(location).get();
            location.setTimeZone(timeZoneId);
            location.setLocalTime(TimeUtils.convertToLocalTimeZone(timeZoneId, location.getUtcTime()));
        }
        return locations;
    }
}
