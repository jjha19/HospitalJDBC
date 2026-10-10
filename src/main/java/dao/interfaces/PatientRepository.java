package dao.interfaces;

import dao.model.PatientEntity;

import java.util.List;

public interface PatientRepository {
    List<PatientEntity> getAll();
    int save(PatientEntity patientEntity);
    void update(PatientEntity patientEntity);
    void delete(int id, boolean confirmation);
}
