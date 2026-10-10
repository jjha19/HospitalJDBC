package dao.repository.JDBC;

import dao.mappers.MedRecordRowMapper;
import dao.model.MedRecordEntity;
import dao.repository.common.SQLQueries;
import domain.exceptions.DatabaseError;
import domain.exceptions.ForeignKeyError;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCMedRecordRepository {
    private final DBConnection connection;
    private final MedRecordRowMapper rowMapper;

    @Inject
    public JDBCMedRecordRepository(DBConnection connection, MedRecordRowMapper rowMapper) {
        this.connection = connection;
        this.rowMapper = rowMapper;
    }

    public List<MedRecordEntity> getMedRecordByPatientId(int patientId) {
        List<MedRecordEntity> medRecords = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            try (Statement st = con.createStatement()) {
                ResultSet rs = st.executeQuery(SQLQueries.SELECT_MEDICAL_RECORDS_BY_PATIENT_ID);
                while (rs.next()) {
                    MedRecordEntity medRecord = rowMapper.mapRow(rs);
                    medRecords.add(medRecord);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medRecords;

    }

    public int save(MedRecordEntity medRecordEntity) {
        try (Connection con = connection.getConnection()) {
            int medrecordID = 0;
            try (PreparedStatement st = con.prepareStatement(SQLQueries.INSERT_MEDICATIONS, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement st2 = con.prepareStatement(SQLQueries.INSERT_MEDRECORD, Statement.RETURN_GENERATED_KEYS)) {
                con.setAutoCommit(false);

                st.setInt(1, medRecordEntity.getRecordId());
                st.setString(2, medRecordEntity.getMedications().toString());

                st2.setInt(1, medRecordEntity.getPatientId());
                st2.setInt(2, medRecordEntity.getDoctorId());
                st2.setString(3, medRecordEntity.getDiagnosis());
                st.setString(4, medRecordEntity.getAdmissionDate());

                st.executeUpdate();
                if (st2.executeUpdate() == 1) {
                    ResultSet rs = st.getGeneratedKeys();
                    if (rs.next()) medrecordID = rs.getInt(1);
                }

                con.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                con.rollback();
                throw new ForeignKeyError(e.getMessage());
            } catch (SQLException e) {
                con.rollback();
                throw new DatabaseError(e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
            return medrecordID;

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    public void update(MedRecordEntity medRecordEntity) {
        try (Connection con = connection.getConnection()){
            try (PreparedStatement st = con.prepareStatement(SQLQueries.UPDATE_MEDRECORDS)) {
                con.setAutoCommit(false);

                con.commit();
            }finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    public void delete(int id) {
        try (Connection con = connection.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(SQLQueries.DELETE_PRESCRIBED_MEDICATIONS_BY_PATIENTID);
                 PreparedStatement st2 = con.prepareStatement(SQLQueries.DELETE_MEDICAL_RECORDS);) {
                con.setAutoCommit(false);

                st.setInt(1, id);
                st2.setInt(1, id);

                st.executeUpdate();
                st2.executeUpdate();
                con.commit();
            } catch (SQLIntegrityConstraintViolationException e){
                con.rollback();
                throw new ForeignKeyError(e.getMessage());
            }finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }
}
