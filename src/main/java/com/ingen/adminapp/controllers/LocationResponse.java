package com.ingen.adminapp.controllers;

import com.ingen.adminapp.apis.Location;

public class LocationResponse {

    private String msg;

    public LocationResponse(String msg) {
        this.msg = msg;
    }

    public static LocationResponse from(Location car) {
        return new LocationResponse("lat: %f | lon: %f".formatted(car.getLat(), car.getLon()));
    }

    public String getMsg() {
        return this.msg;
    }
}
