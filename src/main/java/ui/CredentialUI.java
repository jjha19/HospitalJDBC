package ui;

import domain.DTOs.CredentialDTO;
import domain.service.CredentialService;
import jakarta.inject.Inject;

public class CredentialUI {
    private final CredentialService credentialService;

    @Inject
    public CredentialUI(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    public boolean login(CredentialDTO credentialDTO) {
        return credentialService.getByUsername(credentialDTO);
    }

}