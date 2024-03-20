package com.ingen.adminapp.adapters.out;

import jakarta.persistence.*;

@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String gpsId;

    public Vehicle() {
    }

    public Vehicle(Long id, String gpsId) {
        this.id = id;
        this.gpsId = gpsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGpsId() {
        return gpsId;
    }

    public void setGpsId(String gpsId) {
        this.gpsId = gpsId;
    }
}
