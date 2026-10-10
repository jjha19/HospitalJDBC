package domain.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
        private int id;
        private String name;
        private LocalDate birthDate;
        private String phone;
        private int paid;
        private String userName;
        private String password;
}

