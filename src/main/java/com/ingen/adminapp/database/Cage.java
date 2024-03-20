package com.ingen.adminapp.database;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "cages")
public class Cage {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specimen_id", referencedColumnName = "id")
    private Specimen specimen;
    @Enumerated(EnumType.STRING)
    private CageStatus status;

    public Cage(String id, Specimen specimen, CageStatus status) {
        this.id = UUID.fromString(id);
        this.specimen = specimen;
        this.status = status;

        specimen.setCage(this);
    }

    public Cage() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CageStatus getStatus() {
        return status;
    }

    public void setStatus(CageStatus status) {
        this.status = status;
    }

    public Specimen getSpecimen() {
        return specimen;
    }
}
