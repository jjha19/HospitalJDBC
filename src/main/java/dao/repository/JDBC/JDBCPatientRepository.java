package dao.repository.JDBC;

import common.Constants;
import dao.mappers.PatientRowMapper;
import dao.model.PatientEntity;
import dao.repository.common.SQLQueries;
import domain.exceptions.DatabaseError;
import domain.exceptions.DuplicateUsernameError;
import domain.exceptions.ForeignKeyError;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCPatientRepository implements dao.interfaces.PatientRepository {

    private final DBConnection dbConnection;
    private final PatientRowMapper rowMapper;

    @Inject
    public JDBCPatientRepository(DBConnection dbConnection, PatientRowMapper rowMapper) {
        this.dbConnection = dbConnection;
        this.rowMapper = rowMapper;
    }

    public List<PatientEntity> getAll() {

        List<PatientEntity> patients = new ArrayList<>();
        try (Connection con = dbConnection.getConnection();
             Statement statement = con.createStatement();) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ALL_PATIENTS);
            while (rs.next()) {
                PatientEntity patient = rowMapper.mapRow(rs);
                patients.add(patient);
            }
        } catch (SQLException e) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return patients;
    }

    public int save(PatientEntity patientEntity) {
        int id = 0;
        try (Connection con = dbConnection.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(SQLQueries.INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement statement2 = con.prepareStatement(SQLQueries.INSERT_CREDENTIAL_FOR_PATIENT)) {
                con.setAutoCommit(false);
                //Insert to Patients
                statement.setString(1, patientEntity.getName());
                statement.setObject(2, patientEntity.getBirthDate());
                statement.setString(3, patientEntity.getPhoneNumber());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                    patientEntity.setId(id);
                }

                //Insert to Credentials
                statement2.setString(1, patientEntity.getCredential().getUsername());
                statement2.setString(2, patientEntity.getCredential().getPassword());
                statement2.setInt(3, id);
                statement2.executeUpdate();
                con.commit();
            } catch (SQLIntegrityConstraintViolationException ex) {
                con.rollback();
                throw new DuplicateUsernameError(Constants.DUPLICATE_USERNAME);
            } catch (SQLException e) {
                Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, e);
                con.rollback();
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, e);
        }
        return id;
    }

    public void update(PatientEntity patientEntity) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(SQLQueries.UPDATE_PATIENT)) {
            statement.setString(1, patientEntity.getName());
            statement.setObject(2, patientEntity.getBirthDate());
            statement.setString(3, patientEntity.getPhoneNumber());
            statement.setInt(4, patientEntity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id, boolean confirmation) {
        try (Connection con = dbConnection.getConnection();
        ) {
            con.setAutoCommit(false);
            // If i dont confirm i dont execute this yet
            if (confirmation) {
                try (PreparedStatement statement = con.prepareStatement(SQLQueries.DELETE_MEDICAL_RECORDS);
                     PreparedStatement statement3 = con.prepareStatement(SQLQueries.DELETE_PATIENT_PAYMENTS);
                     PreparedStatement statement4 = con.prepareStatement(SQLQueries.DELETE_PATIENT_APPOINTMENTS);
                     PreparedStatement statement5 = con.prepareStatement(SQLQueries.DELETE_PRESCRIBED_MEDICATIONS_BY_PATIENTID)) {


                    statement.setInt(1, id);
                    statement3.setInt(1, id);
                    statement4.setInt(1, id);
                    statement5.setInt(1, id);

                    statement.executeUpdate();
                    statement3.executeUpdate();
                    statement4.executeUpdate();
                    statement5.executeUpdate();
                }
            }
            //Executed by default
            try (PreparedStatement statement = con.prepareStatement(SQLQueries.DELETE_PATIENT);
                 PreparedStatement statement2 = con.prepareStatement(SQLQueries.DELETE_CREDENTIAL_BY_PATIENT_ID);
            ) {

                statement.setInt(1, id);
                statement2.setInt(1, id);

                statement2.executeUpdate();
                if (statement.executeUpdate() == 0) {
                    throw new DatabaseError(Constants.PATIENT_NOT_FOUND + id);
                }


                con.commit();
            } catch (SQLIntegrityConstraintViolationException ex) {
                con.rollback();
                throw new ForeignKeyError(Constants.FOREIGN_KEY_ERROR);
            } catch (SQLException e) {
                con.rollback();
                Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                con.setAutoCommit(true);
            }


        } catch (SQLException ex) {
            Logger.getLogger(JDBCPatientRepository.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
