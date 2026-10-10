package domain.service;

import dao.repository.JDBC.JDBCDoctorRepository;
import domain.DTOs.DoctorDTO;
import domain.mappers.DoctorDTOMapper;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class DoctorService {
    private final JDBCDoctorRepository jdbcDoctorRepository;

    @Inject
    public DoctorService(JDBCDoctorRepository jdbcDoctorRepository) {
        this.jdbcDoctorRepository = jdbcDoctorRepository;
    }
    public List<DoctorDTO> getAll() {
        List<DoctorDTO> doctorDTOList = new ArrayList<>();
        jdbcDoctorRepository.getAll().forEach(doctorEntity -> {
            DoctorDTO doctorDTO = DoctorDTOMapper.toDto(doctorEntity);
            doctorDTOList.add(doctorDTO);
        });
        return doctorDTOList;
    }
}
