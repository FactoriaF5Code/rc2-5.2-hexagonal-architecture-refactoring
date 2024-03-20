package com.ingen.adminapp.controllers;

import com.ingen.adminapp.apis.CarLocationApi;
import com.ingen.adminapp.apis.CarRemoteApi;
import com.ingen.adminapp.database.CageRepository;
import com.ingen.adminapp.database.CageStatus;
import com.ingen.adminapp.database.SpecimenRepository;
import com.ingen.adminapp.database.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/park")
public class ParkController {

    private final CarLocationApi carLocationApi;
    private final CarRemoteApi carRemoteApi;
    private CageRepository cageRepository;
    private VehicleRepository carRepository;
    private final SpecimenRepository specimenRepository;

    public ParkController(
            @Autowired CageRepository cageRepository,
            @Autowired VehicleRepository carRepository,
            @Autowired CarLocationApi carLocationApi,
            @Autowired CarRemoteApi carRemoteApi,
            SpecimenRepository specimenRepository) {
        this.cageRepository = cageRepository;
        this.carRepository = carRepository;
        this.carLocationApi = carLocationApi;
        this.carRemoteApi = carRemoteApi;
        this.specimenRepository = specimenRepository;
    }

    @PatchMapping("/cages/{id}/close")
    public String closeCage(@PathVariable(name = "id") String cageId) {
        var cage = cageRepository.findById(UUID.fromString(cageId)).get();

        cage.setStatus(CageStatus.CLOSED);
        cageRepository.save(cage);

        return "Cage closed successfully.";
    }

    @PatchMapping("/cages/{id}/open")
    public ResponseEntity<String> openCage(@PathVariable(name = "id") String cageId) {
        var cage = cageRepository.findById(UUID.fromString(cageId)).get();

        if (cage.getSpecimen().getDangerous().equals(true)) {
            return ResponseEntity.badRequest().body("Operation not permitted on this cage");
        }

        cage.setStatus(CageStatus.OPEN);
        cageRepository.save(cage);

        return ResponseEntity.ok("Cage opened successfully.");
    }

    @GetMapping("/cars/{id}/location")
    public String locateCar(@PathVariable(name = "id") String carId) {
        var car = carRepository.findById(Long.valueOf(carId)).get();

        return LocationResponse.from(carLocationApi.findCar(car.getGpsId())).getMsg();
    }

    @PatchMapping("/cars/{id}/start")
    public String startCar(@PathVariable(name = "id") String carId) {
        var car = carRepository.findById(Long.valueOf(carId)).get();

        carRemoteApi.start(car.getId().toString());

        return "Car %s started successfully".formatted(car.getId());
    }

    @PatchMapping("/cars/{id}/stop")
    public String stopCar(@PathVariable(name = "id") String carId) {
        var car = carRepository.findById(Long.valueOf(carId)).get();

        carRemoteApi.stop(car.getId().toString());

        return "Car %s stopped successfully".formatted(car.getId());
    }

    @PatchMapping("/specimens/{id}/sedate")
    public ResponseEntity<String> sedate(@PathVariable(name = "id") Long specimenId) {
        return specimenRepository.findById(specimenId)
                .map(specimen -> {
                    if (specimen.getDosages() > 0) {
                        return ResponseEntity.status(400)
                                .body("This dinosaur has already been sedated!");
                    }
                    Integer dosages = 1;
                    if (specimen.getBig().equals(true)) {
                        dosages += 1;
                    }
                    specimen.setDosages(dosages);
                    specimenRepository.save(specimen);
                    return ResponseEntity
                            .ok(
                                    "The %s has been sedated with %s %s".formatted(
                                       specimen.getName(),
                                       specimen.getDosages(),
                                       specimen.getDosages() > 1 ? "dosages" : "dosage"
                                    ));
                })
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping("/admin/command")
    public String runCommand(@RequestParam(name="command", required = true)String command) {
        if ("accessSecurity".equals(command)) {
            return "Ah ah ah, you didn't say the magic word\n".repeat(100);
        }
        return "ACCESS DENIED";
    }
}
