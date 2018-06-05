package com.company.repository;

import com.company.exception.AppException;
import com.company.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CSVRepository {

    private static final Logger logger = LoggerFactory.getLogger(CSVRepository.class);

    public List<Location> readLocation(final String fileName) {
        final List<Location> locations = new ArrayList<>();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] vals = line.split(",");
                if (vals != null && vals.length == 3) {
                    Location localTime = new Location();
                    localTime.setUtcTime(vals[0]);
                    localTime.setLatitude(vals[1]);
                    localTime.setLongitude(vals[2]);
                    locations.add(localTime);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            logger.error("Unable to load the location data file");
            throw new AppException("102", "Failed to load location data file");
        }
        return locations;
    }

}
