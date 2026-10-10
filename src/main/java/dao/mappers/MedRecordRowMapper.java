package dao.mappers;

import dao.model.MedRecordEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedRecordRowMapper {
    public MedRecordEntity mapRow(ResultSet rs) throws SQLException {
        MedRecordEntity medRecord = new MedRecordEntity();
        medRecord.setRecordId(rs.getInt("record_id"));
        medRecord.setDiagnosis(rs.getString("description"));
        medRecord.setAdmissionDate(rs.getString("date"));
        medRecord.setPatientId(rs.getInt("patient_id"));
        medRecord.setDoctorId(rs.getInt("doctor_id"));
        return medRecord;
    }
}
