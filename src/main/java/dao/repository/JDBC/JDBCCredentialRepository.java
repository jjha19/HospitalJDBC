package dao.repository.JDBC;

import dao.mappers.CredentialRowMapper;
import dao.model.CredentialEntity;
import dao.repository.common.SQLQueries;
import domain.exceptions.DatabaseError;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class JDBCCredentialRepository {
    private final DBConnection dbConnection;
    private final CredentialRowMapper credentialRowMapper;

    @Inject
    public JDBCCredentialRepository(DBConnection dbConnection, CredentialRowMapper credentialRowMapper) {
        this.dbConnection = dbConnection;
        this.credentialRowMapper = credentialRowMapper;
    }

    public CredentialEntity getByUsername(String username) {
        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(SQLQueries.LOGIN)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return credentialRowMapper.mapRow(resultSet);
            }
        } catch (DatabaseError e) {
            Logger.getLogger(JDBCCredentialRepository.class.getName()).severe("Error fetching credential by username: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCCredentialRepository.class.getName()).severe("SQL Error fetching credential by username: " + ex.getMessage());
        }
        return null;
    }

}
