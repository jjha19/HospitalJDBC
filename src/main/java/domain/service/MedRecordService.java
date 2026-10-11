package domain.service;

import dao.repository.JDBC.JDBCMedRecordRepository;
import dao.model.MedRecordEntity;
import domain.DTOs.MedRecordDTO;
import domain.exceptions.DatabaseError;
import domain.exceptions.ForeignKeyError;
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
        try {
            return jdbcMedRecordRepository.getMedRecordByPatientId(patientId).stream().map(MedicalRecordDTOMapper::toDto).toList();
        }catch (DatabaseError e){
            throw new DatabaseError(e.getMessage());
        }
    }

    public int save(MedRecordDTO medRecordDTO) {
        try {
            return jdbcMedRecordRepository.save(MedicalRecordDTOMapper.toEntity(medRecordDTO));
        } catch (ForeignKeyError e) {
            throw new ForeignKeyError(e.getMessage());
        }catch (DatabaseError e){
            throw new DatabaseError(e.getMessage());
        }
    }

    public void update(MedRecordDTO medRecordDTO) {
        try {
            jdbcMedRecordRepository.update(MedicalRecordDTOMapper.toEntity(medRecordDTO));
        }catch (DatabaseError e){
            throw new DatabaseError(e.getMessage());
        }
    }

    public void delete(int id) {
        try {
            jdbcMedRecordRepository.delete(id);
        }catch (DatabaseError e){
            throw new DatabaseError(e.getMessage());
        }
    }
}
