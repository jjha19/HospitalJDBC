package domain.mappers;

import dao.model.DoctorEntity;
import domain.DTOs.DoctorDTO;

public class DoctorDTOMapper {
    public static DoctorEntity toEntity(DoctorDTO doctorDTO){
        return new DoctorEntity(
                doctorDTO.getId(),
                doctorDTO.getName(),
                doctorDTO.getSpecialization(),
                doctorDTO.getPhone()
        );
    }

    public static DoctorDTO toDto(DoctorEntity doctorEntity){
        return new DoctorDTO(
                doctorEntity.getDoctorId(),
                doctorEntity.getName(),
                doctorEntity.getSpecialization(),
                doctorEntity.getPhone()
        );
    }
}
