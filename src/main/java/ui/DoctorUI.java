package ui;

import domain.DTOs.DoctorDTO;
import domain.service.DoctorService;
import jakarta.inject.Inject;

import java.util.List;

public class DoctorUI {
    private final DoctorService doctorService;

    @Inject
    public DoctorUI(DoctorService doctorService) {this.doctorService = doctorService;}

    public List<DoctorDTO> getAll() {
        return doctorService.getAll();
    }

}
