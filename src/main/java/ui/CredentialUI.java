package ui;

import domain.DTOs.CredentialDTO;
import domain.exceptions.DatabaseError;
import domain.service.CredentialService;
import jakarta.inject.Inject;

public class CredentialUI {
    private final CredentialService credentialService;

    @Inject
    public CredentialUI(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    public boolean login(CredentialDTO credentialDTO) {
        try {
            return credentialService.getByUsername(credentialDTO);
        }catch (DatabaseError e){
            throw new DatabaseError(e.getMessage());
        }
    }

}