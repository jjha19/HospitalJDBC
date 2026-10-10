package dao.mappers;

import dao.model.DoctorEntity;

public class DoctorRowMapper {
    public DoctorEntity mapRow(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        return new DoctorEntity(
                resultSet.getInt("doctor_id"),
                resultSet.getString("name"),
                resultSet.getString("specialization"),
                resultSet.getString("phone")
        );
    }
}
