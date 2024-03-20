package com.ingen.adminapp;

import com.ingen.adminapp.database.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private MockMvc api;

    @Autowired
    private CageRepository cageRepository;

    @Autowired
    private SpecimenRepository specimenRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {
        cageRepository.deleteAll();
        specimenRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

    @Test
    @DisplayName("Close an open gate")
    public void testCloseGate() throws Exception {

        cageRepository.save(new Cage("8ad274e1-dadd-457f-8e46-ab11c1dc6ae7",
                new Specimen(1L, "Gallimimus", false, false),
                CageStatus.OPEN
        ));

        api.perform(
                        patch("/park/cages/8ad274e1-dadd-457f-8e46-ab11c1dc6ae7/close")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Cage closed successfully."));
    }

    @Test
    @DisplayName("open a closed gate")
    public void testOpenGate() throws Exception {

        cageRepository.save(new Cage("8ad274e1-dadd-457f-8e46-ab11c1dc6ae7",
                new Specimen(1L, "Gallimimus", false, false),
                CageStatus.CLOSED
        ));

        api.perform(
                        patch("/park/cages/8ad274e1-dadd-457f-8e46-ab11c1dc6ae7/open")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Cage opened successfully."));
    }

    @Test
    @DisplayName("Only the non-dangerous dinosaurs cells can be open")
    public void testCannotOpenGateOfDangerousSpecimens() throws Exception {
        cageRepository.save(new Cage("8ad274e1-dadd-457f-8e46-ab11c1dc6ae7",
                new Specimen(1L, "Tiranosaurus", true, true),
                CageStatus.CLOSED
        ));

        api.perform(
                        patch("/park/cages/8ad274e1-dadd-457f-8e46-ab11c1dc6ae7/open")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Operation not permitted on this cage"));
    }

    @Test
    @DisplayName("Get car location (coordinates)")
    public void getCarLocation() throws Exception {


        vehicleRepository.save(new Vehicle(1L, "VEI7239UR923425"));

        api.perform(
                        get("/park/cars/1/location")
                )
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Start/Stop car")
    public void testStartStopCar() throws Exception {

        vehicleRepository.save(new Vehicle(1L, "VEI7239UR923425"));

        api.perform(patch("/park/cars/1/start"))
                .andExpect(status().isOk())
                .andExpect(content().string("Car 1 started successfully"));

        api.perform(patch("/park/cars/1/stop"))
                .andExpect(status().isOk())
                .andExpect(content().string("Car 1 stopped successfully"));
    }

    @Test
    @DisplayName("Sedate regular dinosaur")
    public void testSedateRegularDinosaur() throws Exception {
        cageRepository.save(new Cage("8ad274e1-dadd-457f-8e46-ab11c1dc6ae7",
                new Specimen(1L, "Velociraptor", false, false),
                CageStatus.CLOSED
        ));

        api.perform(patch("/park/specimens/1/sedate"))
                .andExpect(status().isOk())
                .andExpect(content().string("The Velociraptor has been sedated with 1 dosage"));

        var dino = specimenRepository.findById(1L).get();
        assertThat(dino.getDosages(), equalTo(1));
    }

    @Test
    @DisplayName("Sedate dinosaur")
    public void testSedateBigDinosaur() throws Exception {
        cageRepository.save(new Cage("8ad274e1-dadd-457f-8e46-ab11c1dc6ae7",
                new Specimen(1L, "Triceratops", false, true),
                CageStatus.CLOSED
        ));

        api.perform(patch("/park/specimens/1/sedate"))
                .andExpect(status().isOk())
                .andExpect(content().string("The Triceratops has been sedated with 2 dosages"));

        var dino = specimenRepository.findById(1L).get();
        assertThat(dino.getDosages(), equalTo(2));
    }

    @Test
    @DisplayName("If the dinosaur is already sedated, it cannot be sedated again")
    public void testCannotSedateMultipleTimes() throws Exception {
        cageRepository.save(new Cage("8ad274e1-dadd-457f-8e46-ab11c1dc6ae7",
                new Specimen(1L, "Velociraptor", false, false),
                CageStatus.CLOSED
        ));

        api.perform(patch("/park/specimens/1/sedate"))
                .andExpect(status().isOk());

        api.perform(patch("/park/specimens/1/sedate"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("This dinosaur has already been sedated!"));

        var dino = specimenRepository.findById(1L).get();
        assertThat(dino.getDosages(), equalTo(1));
    }
}
