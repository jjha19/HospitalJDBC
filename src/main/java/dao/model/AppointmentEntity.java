package dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentEntity {
    private int appointment_id;
    private int patientId;
    private int doctorId;
    private LocalDate appointmentDate;
}
