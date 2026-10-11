package dao.repository.common;

public class SQLQueries {
    public static final String SELECT_ALL_PATIENTS = "SELECT * FROM patients";
    public static final String INSERT_PATIENT = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";

    public static final String UPDATE_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?";
    public static final String DELETE_PATIENT = "DELETE FROM patients WHERE patient_id = ?";
    public static final String SELECT_ALL_DOCTORS = "SELECT * FROM doctors";
    public static final String LOGIN = "SELECT * FROM credentials WHERE username = ?";

    public static final String INSERT_CREDENTIAL_FOR_PATIENT = "INSERT INTO credentials (username, password, patient_id) VALUES (?, ?, ?)";

    public static final String DELETE_CREDENTIAL_BY_USERNAME = "DELETE FROM credentials WHERE username = ?";
    public static final String DELETE_CREDENTIAL_BY_PATIENT_ID = "DELETE FROM credentials WHERE patient_id = ?";
    public static final String DELETE_PATIENT_APPOINTMENTS_BY_PATIENTID = "DELETE FROM appointments WHERE patient_id = ?";
    public static final String DELETE_PATIENT_PAYMENTS_BY_PATIENTID = "DELETE FROM patient_payments WHERE patient_id = ?";
    public static final String DELETE_MEDICAL_RECORDS_BY_RECORDID = "DELETE FROM medical_records WHERE record_id = ?";
    public static final String DELETE_PRESCRIBED_MEDICATIONS_BY_PATIENTID = "DELETE FROM prescribed_medications WHERE record_id in (SELECT record_id FROM medical_records WHERE patient_id = ?)";
    public static final String DELETE_PRESCRIBED_MEDICATIONS_BY_RECORDID = "DELETE FROM prescribed_medications WHERE record_id = ?";
    public static final String DELETE_MEDICAL_RECORD_BY_PATIENTID = "DELETE FROM medical_records WHERE patient_id = ?";


    public static final String SELECT_MEDICAL_RECORDS_BY_PATIENT_ID = "SELECT mr.*, pm.medication_name FROM medical_records mr left join prescribed_medications pm on mr.record_id = pm.record_id WHERE patient_id = ?";
    public static final String INSERT_MEDICATIONS = "iNSERT INTO prescribed_medications (record_id, medication_name) values (?, ?)";
    public static final String INSERT_MEDRECORD = "INSERT INTO medical_records (patient_id, doctor_id, diagnosis, admission_date) values (?, ?, ?, ?)";
    public static final String UPDATE_MEDRECORDS = "UPDATE medical_records set patient_id = ?, doctor_id = ?, diagnosis = ?, admission_date = ? where record_id = ?";
    public static final String UPDATE_PRESCRIBED_MEDICATIONS = "UPDATE prescribed_medications set medication_name = ? where record_id = ?";
}
