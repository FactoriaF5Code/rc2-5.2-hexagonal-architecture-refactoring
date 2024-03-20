package com.ingen.adminapp.apis;

import org.springframework.stereotype.Component;


@Component
public class CarLocationApi {
    public Location findCar(String carId) {
        return new Location(
                -90.0 + Math.random() * 180.0,
                -180.0 + Math.random() * 360.0
        );
    }
}
