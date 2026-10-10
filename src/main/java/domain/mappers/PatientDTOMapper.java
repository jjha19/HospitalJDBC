package domain.mappers;


import dao.model.CredentialEntity;
import dao.model.PatientEntity;
import domain.DTOs.PatientDTO;

public class PatientDTOMapper {
    public static PatientEntity toEntity(PatientDTO patientDTO) {
        return new PatientEntity(1,
                patientDTO.getName(),
                patientDTO.getBirthDate(),
                patientDTO.getPhone(),
                new CredentialEntity(0,patientDTO.getUserName(), patientDTO.getPassword()));
    }

    public static PatientDTO toDto(PatientEntity patientEntity) {
        return new PatientDTO(
                patientEntity.getId(),
                patientEntity.getName(),
                patientEntity.getBirthDate(),
                patientEntity.getPhoneNumber(),
                0,
                null,
                null
        );
    }

}