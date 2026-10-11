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
            try (PreparedStatement st = con.prepareStatement(SQLQueries.SELECT_MEDICAL_RECORDS_BY_PATIENT_ID)) {
                st.setInt(1, patientId);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    MedRecordEntity medRecord = rowMapper.mapRow(rs);
                    medRecords.add(medRecord);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
        return medRecords;

    }

    public int save(MedRecordEntity medRecordEntity) {
        try (Connection con = connection.getConnection()) {
            int medrecordID = 0;
            try (PreparedStatement insert_record = con.prepareStatement(SQLQueries.INSERT_MEDRECORD, Statement.RETURN_GENERATED_KEYS)) {
                PreparedStatement insert_med = con.prepareStatement(SQLQueries.INSERT_MEDICATIONS, Statement.RETURN_GENERATED_KEYS);
                con.setAutoCommit(false);

                insert_record.setInt(1, medRecordEntity.getPatientId());
                insert_record.setInt(2, medRecordEntity.getDoctorId());
                insert_record.setString(3, medRecordEntity.getDiagnosis());
                insert_record.setString(4, medRecordEntity.getAdmissionDate());

                if (insert_record.executeUpdate() == 1) {
                    ResultSet rs = insert_record.getGeneratedKeys();
                    if (rs.next()){
                        medrecordID = rs.getInt(1);
                    }
                }else throw new DatabaseError("Failed to retrieve generated record ID");

                insert_med.setInt(1, medrecordID);
                insert_med.setString(2, medRecordEntity.getMedications().toString());


                insert_med.executeUpdate();

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
        try (Connection con = connection.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(SQLQueries.UPDATE_MEDRECORDS);
                 PreparedStatement st2 = con.prepareStatement(SQLQueries.UPDATE_PRESCRIBED_MEDICATIONS)) {
                con.setAutoCommit(false);
                st.setInt(1, medRecordEntity.getPatientId());
                st.setInt(2, medRecordEntity.getDoctorId());
                st.setString(3, medRecordEntity.getDiagnosis());
                st.setString(4, medRecordEntity.getAdmissionDate());
                st.setInt(5, medRecordEntity.getRecordId());

                st2.setString(1, medRecordEntity.getMedications().toString());
                st2.setInt(2, medRecordEntity.getRecordId());

                st2.executeUpdate();
                st.executeUpdate();

                con.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                con.rollback();
                throw new ForeignKeyError(e.getMessage());
            } catch (SQLException e) {
                con.rollback();
                throw new DatabaseError(e.getMessage());

            }finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }

    public void delete(int id) {
        try (Connection con = connection.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(SQLQueries.DELETE_PRESCRIBED_MEDICATIONS_BY_RECORDID);
                 PreparedStatement st2 = con.prepareStatement(SQLQueries.DELETE_MEDICAL_RECORDS_BY_RECORDID);) {
                con.setAutoCommit(false);

                st.setInt(1, id);
                st2.setInt(1, id);

                st.executeUpdate();
                st2.executeUpdate();
                con.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                con.rollback();
                throw new ForeignKeyError(e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DatabaseError(e.getMessage());
        }
    }
}
