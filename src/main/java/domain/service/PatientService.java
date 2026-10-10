package domain.service;

import dao.repository.JDBC.JDBCPatientRepository;
import domain.DTOs.PatientDTO;
import domain.exceptions.ForeignKeyError;
import domain.mappers.PatientDTOMapper;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private final JDBCPatientRepository jdbcPatientRepository;

    @Inject
    public PatientService(JDBCPatientRepository jdbcPatientRepository) {
        this.jdbcPatientRepository = jdbcPatientRepository;
    }

    public List<PatientDTO> getAll() {
        List<PatientDTO> patientDTOList = new ArrayList<>();
        jdbcPatientRepository.getAll().forEach(patientEntity -> {
            PatientDTO patientDTO = PatientDTOMapper.toDto(patientEntity);
            patientDTOList.add(patientDTO);
        });
        return patientDTOList;
    }

    public int add(PatientDTO patientDTO) {
        return jdbcPatientRepository.save(PatientDTOMapper.toEntity(patientDTO));
    }
    public void update(PatientDTO patientDTO) {
        jdbcPatientRepository.update(PatientDTOMapper.toEntity(patientDTO));

    }
    public void delete(int id, boolean confirmation) {
        try {
            jdbcPatientRepository.delete(id, confirmation);
        }catch (ForeignKeyError e){
            throw new ForeignKeyError(e.getMessage());
        }
    }
}
