package com.ingen.adminapp.database;

import jakarta.persistence.*;

@Entity
@Table(name = "specimens")
public class Specimen {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Boolean isDangerous;
    private Boolean isBig;
    private Integer dosages = 0;

    public void setName(String name) {
        this.name = name;
    }

    public void setDangerous(Boolean dangerous) {
        isDangerous = dangerous;
    }

    public void setBig(Boolean big) {
        isBig = big;
    }


    @OneToOne(mappedBy = "specimen")
    private Cage cage;

    public Specimen(Long id, String name, Boolean isDangerous, Boolean isBig) {
        this.id = id;
        this.name = name;
        this.isDangerous = isDangerous;
        this.isBig = isBig;
    }

    public Specimen() {

    }

    public String getName() {
        return name;
    }

    public Boolean getDangerous() {
        return isDangerous;
    }

    public Boolean getBig() {
        return isBig;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cage getCage() {
        return cage;
    }

    public void setCage(Cage cage) {
        this.cage = cage;
    }

    public Integer getDosages() {
        return dosages;
    }

    public void setDosages(Integer dosages) {
        this.dosages = dosages;
    }
}
