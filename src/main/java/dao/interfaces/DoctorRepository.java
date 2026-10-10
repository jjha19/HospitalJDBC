package dao.interfaces;

import dao.model.DoctorEntity;

import java.util.List;

public interface DoctorRepository {
    List<DoctorEntity> getAll();
}
