package ui;

import dao.model.PatientEntity;
import domain.DTOs.PatientDTO;
import domain.exceptions.AppError;
import domain.exceptions.DatabaseError;
import domain.exceptions.ForeignKeyError;
import domain.service.PatientService;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class PatientUi {
    private final PatientService patientsService;

    @Inject
    public PatientUi(PatientService patientsService) {
        this.patientsService = patientsService;
    }
    public List<PatientDTO> getAll() {
        List<PatientDTO> patientDTOList = new ArrayList<>();
        try {
            patientDTOList =  patientsService.getAll();
        } catch (DatabaseError e) {
            Logger.getLogger(PatientUi.class.getName()).severe("Database error occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new AppError(e.getMessage());
        }
        return patientDTOList;
    }

    public int save(PatientDTO patientDTO) {
        return patientsService.add(patientDTO);
    }

    public void update(PatientDTO patientDTO) {
        patientsService.update(patientDTO);
    }

    public void delete(int id) {
        try {
            patientsService.delete(id, false);
        }catch (ForeignKeyError e){
            System.out.println("This patient has associated medical records. Are you sure you want to delete it? (y/n)");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                patientsService.delete(id, true);
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
    }
}
