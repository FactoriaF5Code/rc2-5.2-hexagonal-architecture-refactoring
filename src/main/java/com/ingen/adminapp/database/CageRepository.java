package com.ingen.adminapp.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CageRepository extends JpaRepository<Cage, UUID> {
}
