package ui;

import domain.DTOs.CredentialDTO;
import domain.DTOs.MedRecordDTO;
import domain.DTOs.PatientDTO;
import domain.exceptions.AppError;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        try {
            SeContainerInitializer initializer = SeContainerInitializer.newInstance();
            final SeContainer container = initializer.initialize();

            CredentialUI credentialUI = container.select(CredentialUI.class).get();
            PatientUi patientUi = container.select(PatientUi.class).get();
            DoctorUI doctorUi = container.select(DoctorUI.class).get();
            MedRecordUI medRecordUi = container.select(MedRecordUI.class).get();
            Scanner sc = new Scanner(System.in);

            Boolean loggedIn = false;
            do {
                System.out.println("""
                        Hospital CRUD Applications
                        Please log in to continue
                        Username:\s""");
                String username = sc.nextLine();
                System.out.println("Password: ");
                String password = sc.nextLine();
                CredentialDTO credentialDTO = new CredentialDTO();
                credentialDTO.setUsername(username);
                credentialDTO.setPassword(password);
                loggedIn = credentialUI.login(credentialDTO);
                if (!loggedIn) {
                    System.out.println("Invalid username or password. Please try again.");
                }
            } while (!loggedIn);
            System.out.println("Log in Succesful! Welcome");
            int option;
            do {
                System.out.println("""
                        Main Menu
                        1. Get all Patients
                        2. Add PatientEntity
                        3. Update Patient
                        4. Delete Patient
                        5. Get all Doctors
                        6. Get all MedRecords by Patient
                        7. Add MedRecords
                        8. Update MedRecords
                        9. Delete MedRecords
                        10. Exit
                        """);
                option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1:
                        try {
                            System.out.println("Get all Patients");
                            patientUi.getAll().forEach(p -> System.out.println("  -> " + p));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        try {
                            LocalDate birthDate = null; // Default value
                            System.out.println("Add Patient");
                            System.out.println("What's the patient's name?");
                            String patientName = sc.nextLine();
                            birthDate = LocalDate.parse("2000-01-01");
                            try {
                                System.out.println("What's the patient's new birth date? (yyyy-mm-dd)");
                                birthDate = LocalDate.parse(sc.nextLine());
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Using default date: 2000-01-01");
                            }
                            ;
                            System.out.println("What's the patient's phone number?");
                            String patientPhoneNumber = sc.nextLine();
                            System.out.println("What's the patient's username?");
                            String patientUsername = sc.nextLine();
                            System.out.println("What's the patient's password?");
                            String patientPassword = sc.nextLine();

                            PatientDTO patientDTO = new PatientDTO(0, patientName, birthDate, patientPhoneNumber, 0,patientUsername , patientPassword);

                            System.out.println("Adding patient with the ID: " + patientUi.save(patientDTO));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 3:
                        try {
                            System.out.println("Update Patient");
                            System.out.println("Whats the patient's id?");
                            int id = sc.nextInt();
                            sc.nextLine();
                            System.out.println("What's the patient's new name?");
                            String name = sc.nextLine();
                            LocalDate birthDate = LocalDate.parse("2000-01-01"); // Default value
                            try {
                                System.out.println("What's the patient's new birth date? (yyyy-mm-dd)");
                                birthDate = LocalDate.parse(sc.nextLine());
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Using default date: 2000-01-01");
                            }
                            System.out.println("What's the patient's new phone number?");
                            String phoneNumber = sc.nextLine();
                            System.out.println("What's the patient's new username?");
                            String username = sc.nextLine();
                            System.out.println("What's the patient's new password?");
                            String password = sc.nextLine();

                            PatientDTO patient = new PatientDTO(id, name, birthDate, phoneNumber, 0, username, password);
                            System.out.println("Updating patient with the ID: " + id);
                            patientUi.update(patient);
                        } catch (Exception e) {
                            System.err.println("Error updating patient: " + e.getMessage());
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        break;
                    case 4:
                        try {
                            System.out.println("Delete Patient");
                            System.out.println("Whats the patient's id?");
                            int idPatient = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Deleting patient with the ID: " + idPatient);
                            patientUi.delete(idPatient);
                        } catch (Exception e) {
                            System.err.println("Error deleting patient: " + e.getMessage());
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        break;
                    case 5:
                        System.out.println("Get all Doctors");
                        doctorUi.getAll().forEach(p -> System.out.println("  -> " + p));
                        break;
                    case 6:
                        try {
                            System.out.println("Get all MedRecords by Patient");
                            System.out.println("Enter the patient ID to retrieve the medical record:");
                            int patientId = sc.nextInt();
                            sc.nextLine();
                            medRecordUi.getMedRecordByPatientId(patientId).forEach(medRecord -> System.out.println("  -> " + medRecord));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 7:
                        System.out.println("Add MedRecords");
                        // TODO: add medical records and medications at the same time
                        try {
                            System.out.println("Enter the patient id to add the medical record:");
                            int patientId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the doctor id to add the medical record:");
                            int doctorId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the diagnosis");
                            String diagnosis = sc.nextLine();
                            System.out.println("Enter the admission date YYYY-MM-DD");
                            String admissionDate = sc.nextLine();
                            System.out.println("Enter the medications prescribed separated by a comma");
                            String answer = sc.nextLine();
                            List<String> medications = new ArrayList<>(Arrays.stream(answer.split(",")).toList());
                            MedRecordDTO medRecordDTO = new MedRecordDTO(0, diagnosis, admissionDate, patientId, doctorId, medications);
                            medRecordUi.save(medRecordDTO);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 8:
                        try {
                            System.out.println("Update MedRecords");
                            System.out.println("Enter the medical record ID to update:");
                            int idrecord = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the new medical record description:");
                            String description = sc.nextLine();
                            System.out.println("Enter the new medical record date (yyyy-mm-dd):");
                            String date = sc.nextLine();
                            System.out.println("Enter the new patient ID:");
                            int idPatient = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the new doctor ID:");
                            int idDoctor = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the new medications (comma-separated):");
                            String medicationsStr = sc.nextLine();
                            java.util.List<String> medications = java.util.Arrays.asList(medicationsStr.split(","));
                            MedRecordDTO medRecordDTO = new MedRecordDTO(idrecord, description, date, idPatient, idDoctor, medications);
                            medRecordUi.update(medRecordDTO);
                        } catch (Exception e) {
                            System.err.println("Error updating medical record: " + e.getMessage());
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        break;
                    case 9:
                        System.out.println("Delete MedRecords");

                        //TODO: Delete all medications and then medrecords no questions asked
                        medRecordUi.delete();
                        break;
                    case 10:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } while (option != 10);
        } catch (AppError e) {
            System.err.println("Critical error: " + e.getMessage());
            System.exit(1);
        }

    }
}
