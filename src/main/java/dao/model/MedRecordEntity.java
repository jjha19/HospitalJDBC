package dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedRecordEntity {
    public int recordId;
    public int patientId;
    public int doctorId;
    public String diagnosis;
    public String admissionDate;
    public List<String> medications;

}
