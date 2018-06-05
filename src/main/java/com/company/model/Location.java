package com.company.model;

public class Location {

    private String utcTime;

    private String latitude;

    private String longitude;

    private String timeZone;

    private String localTime;

    public String getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(final String utcTime) {
        this.utcTime = utcTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }
}
