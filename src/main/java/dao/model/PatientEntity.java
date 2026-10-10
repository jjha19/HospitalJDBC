package dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PatientEntity {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private CredentialEntity credential;
}
