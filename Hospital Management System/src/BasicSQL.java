public class BasicSQL {
    protected static final String URL = "jdbc:mysql://localhost:3306/hospital-db";
    protected static final String USERNAME = "root";
    protected static final String PASSWORD = "Rjdp@123";
    protected static final String VIEW_DOCTORS = "select * from doctors";
    protected static final String GET_DOCTOR_ID = "SELECT * FROM doctors WHERE idDoctor = ?";
    protected static final String ADD_PATIENTS = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
    protected static final String VIEW_PATIENTS = "select * from patients";
    protected static final String GET_PATIENT_ID = "SELECT * FROM patients WHERE idPatient = ?";
    protected static final String APPOINTMENT = "INSERT INTO appointments(Patient_id, Doctor_id, Appointment_date) VALUES(?, ?, ?)";
    protected static final String DOCTOR_AVAILABILITY = "SELECT COUNT(*) FROM appointments WHERE Doctor_id = ? AND Appointment_date = ?";
}
