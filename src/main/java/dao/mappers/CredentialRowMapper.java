package dao.mappers;

import dao.model.CredentialEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialRowMapper {
    public CredentialEntity mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String username = rs.getString("username");
        String password = rs.getString("password");

        return new CredentialEntity(id, username, password);
    }
}
