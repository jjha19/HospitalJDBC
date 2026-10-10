package dao.mappers;

import dao.model.CredentialEntity;
import dao.model.PatientEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PatientRowMapper {
    public PatientEntity mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("patient_id");
        String name = rs.getString("name");
        LocalDate birthDate = rs.getDate("date_of_birth").toLocalDate();
        String phoneNumber = rs.getString("phone");

        return new PatientEntity(id, name, birthDate, phoneNumber, null);
    }
}
