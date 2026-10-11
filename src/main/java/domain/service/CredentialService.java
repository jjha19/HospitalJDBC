package domain.service;

import dao.model.CredentialEntity;
import dao.repository.JDBC.JDBCCredentialRepository;
import domain.DTOs.CredentialDTO;
import domain.exceptions.DatabaseError;
import domain.exceptions.NoUserFound;
import domain.mappers.CredentialMapper;
import jakarta.inject.Inject;

public class CredentialService {
    private final JDBCCredentialRepository jdbcCredentialRepository;

    @Inject
    public CredentialService(JDBCCredentialRepository jdbcCredentialRepository) {
        this.jdbcCredentialRepository = jdbcCredentialRepository;
    }
    public boolean getByUsername(CredentialDTO credentialDTO) {
        try {
            CredentialEntity credential = jdbcCredentialRepository.getByUsername(credentialDTO.getUsername());
            return credential.getUsername().equals(credentialDTO.getUsername())
                    && credential.getPassword().equals(credentialDTO.getPassword());
        }catch (NoUserFound | DatabaseError e){
            throw new DatabaseError(e.getMessage());
        }
    }

}
