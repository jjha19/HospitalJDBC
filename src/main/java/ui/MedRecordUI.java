package ui;

import domain.DTOs.MedRecordDTO;
import domain.exceptions.DatabaseError;
import domain.exceptions.ForeignKeyError;
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
        try {
            return medRecordService.save(medRecordDTO);
        } catch (ForeignKeyError e) {
            throw new ForeignKeyError(e.getMessage());
        }catch (DatabaseError e){
            throw new DatabaseError(e.getMessage());
        }
    }

    public void update(MedRecordDTO medRecordDTO) {
        try {
            medRecordService.update(medRecordDTO);
        } catch (DatabaseError e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    public void delete(int id) {
        try {
            medRecordService.delete(id);
        }catch (DatabaseError e) {
            throw new DatabaseError(e.getMessage());
        }
    }
}
