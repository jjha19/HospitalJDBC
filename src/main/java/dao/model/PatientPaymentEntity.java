package dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PatientPaymentEntity {
    private int paymentId;
    private int patientId;
    private double amount;
    private LocalDate paymentDate;
}
