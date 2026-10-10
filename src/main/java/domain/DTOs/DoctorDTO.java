package domain.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
public class DoctorDTO {
    private int id;
    private String name;
    private String specialization;
    private String phone;
}
