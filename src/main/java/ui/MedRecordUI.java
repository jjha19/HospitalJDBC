package ui;

import domain.DTOs.MedRecordDTO;
import domain.service.MedRecordService;
import jakarta.inject.Inject;

import java.util.List;

public class MedRecordUI {
    private final MedRecordService medRecordService;

    @Inject
    public MedRecordUI(MedRecordService medRecordService) {
        this.medRecordService = medRecordService;
    }
    public List<MedRecordDTO> getMedRecordByPatientId(int patientId) {
        System.out.println("Enter the patient ID to retrieve the medical record:");

        return medRecordService.getMedRecordByPatientId(patientId);
    }

    public int save(MedRecordDTO medRecordDTO) {
        return medRecordService.save(medRecordDTO);
    }

    public void update(MedRecordDTO medRecordDTO) {
        medRecordService.update(medRecordDTO);
    }

    public void delete() {
        System.out.println("Enter the medical record ID to delete:");
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int id = sc.nextInt();
        sc.nextLine(); // Consume the enter
        medRecordService.delete(id);
    }
}
