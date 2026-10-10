package domain.mappers;

import dao.model.CredentialEntity;
import domain.DTOs.CredentialDTO;

public class CredentialMapper {
    public static CredentialDTO toDTO(CredentialEntity credentialEntity) {
        return new CredentialDTO(credentialEntity.getUsername(), credentialEntity.getPassword());
    }
    public static CredentialEntity toEntity(CredentialDTO credentialDTO) {
        return new CredentialEntity(0, credentialDTO.getUsername(), credentialDTO.getPassword());

    }
}
