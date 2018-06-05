package com.company;

import com.company.config.AppConfig;
import com.company.model.Location;
import com.company.service.LocationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static com.company.utils.Constants.LOCATION_SERVICE;

public class Application {

    public static void main(String[] args) {
        try {
            final LocationService locationService = getService();
            final List<Location> locations = locationService.findLocalTime("test_file.csv");
            for (Location location : locations) {
                System.out.println(location.getUtcTime() + "," + location.getLatitude() + "," + location.getLongitude()
                        + "," + location.getTimeZone() + "," + location.getLocalTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LocationService getService() {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        final LocationService loanService = (LocationService) ctx.getBean(LOCATION_SERVICE);
        ctx.close();
        return loanService;
    }
}
