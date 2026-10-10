package dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DoctorEntity {
    private int doctorId;
    private String name;
    private String specialization;
    private String phone;

}
