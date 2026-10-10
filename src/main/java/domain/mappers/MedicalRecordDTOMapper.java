package domain.mappers;

import dao.model.MedRecordEntity;
import domain.DTOs.MedRecordDTO;

public class MedicalRecordDTOMapper {

    public static MedRecordEntity toEntity(MedRecordDTO medicalRecordDTO) {
        return new MedRecordEntity(
                medicalRecordDTO.getId(),
                medicalRecordDTO.getIdPatient(),
                medicalRecordDTO.getIdDoctor(),
                medicalRecordDTO.getDescription(),
                medicalRecordDTO.getDate(),
                medicalRecordDTO.getMedications()
        );
    }

    public static MedRecordDTO toDto(MedRecordEntity medicalRecord) {
        return new MedRecordDTO(
                medicalRecord.getRecordId(),
                medicalRecord.getDiagnosis(),
                medicalRecord.getAdmissionDate(),
                medicalRecord.getPatientId(),
                medicalRecord.getDoctorId(),
                null
        );
    }
}
