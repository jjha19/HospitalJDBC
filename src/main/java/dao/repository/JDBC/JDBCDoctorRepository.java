package dao.repository.JDBC;

import dao.mappers.DoctorRowMapper;
import dao.model.DoctorEntity;
import dao.repository.common.SQLQueries;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCDoctorRepository implements dao.interfaces.DoctorRepository {
    private final DBConnection connection;
    private final DoctorRowMapper doctorRowMapper;

    @Inject
    public JDBCDoctorRepository(DBConnection connection, DoctorRowMapper doctorRowMapper) {
        this.connection = connection;
        this.doctorRowMapper = doctorRowMapper;
    }
    public List<DoctorEntity> getAll() {
        List<DoctorEntity> doctors = new ArrayList<>();
        try (Statement statement = connection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLQueries.SELECT_ALL_DOCTORS)){
            while (resultSet.next()) {
                DoctorEntity doctorEntity = doctorRowMapper.mapRow(resultSet);
                doctors.add(doctorEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctors;
    }
}
