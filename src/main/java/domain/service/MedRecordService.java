package domain.service;

import dao.repository.JDBC.JDBCMedRecordRepository;
import dao.model.MedRecordEntity;
import domain.DTOs.MedRecordDTO;
import domain.mappers.MedicalRecordDTOMapper;
import jakarta.inject.Inject;

import java.util.List;

public class MedRecordService {
    private final JDBCMedRecordRepository jdbcMedRecordRepository;

    @Inject
    public MedRecordService(JDBCMedRecordRepository jdbcMedRecordRepository) {
        this.jdbcMedRecordRepository = jdbcMedRecordRepository;
    }

    public List<MedRecordDTO> getMedRecordByPatientId(int patientId) {
        return jdbcMedRecordRepository.getMedRecordByPatientId(patientId).stream().map(MedicalRecordDTOMapper::toDto).toList();
    }

    public int save(MedRecordDTO medRecordDTO) {
        return jdbcMedRecordRepository.save(MedicalRecordDTOMapper.toEntity(medRecordDTO));
    }

    public void update(MedRecordDTO medRecordDTO) {
        jdbcMedRecordRepository.update(MedicalRecordDTOMapper.toEntity(medRecordDTO));
    }

    public void delete(int id) {
        jdbcMedRecordRepository.delete(id);
    }
}
