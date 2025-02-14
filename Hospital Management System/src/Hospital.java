import java.sql.*;
import java.util.Scanner;

public class Hospital {
    public static void main(String[] args) throws SQLException{

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        try(Connection connection = DriverManager.getConnection(BasicSQL.URL, BasicSQL.USERNAME, BasicSQL.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM appointments WHERE id = ?")){

            Patient patient = new Patient(connection, scanner, preparedStatement);
            Doctor doctor = new Doctor(connection,preparedStatement);

            while(true){
                System.out.print("""
                HOSPITAL MANAGEMENT SYSTEM
                1. Add Patient
                2. View Patient
                3. View Doctors
                4. Book Appointment
                5. Exit
                Enter your choice: """);

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice){
                    case 1 -> patient.addPatient();
                    case 2 -> patient.viewPatients();
                    case 3 -> doctor.viewDoctors();
                    case 4 -> bookAppointment(patient, doctor, connection, scanner, preparedStatement);
                    case 5 -> {
                        preparedStatement.close();
                        scanner.close();
                        connection.close();
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    }default -> System.out.println("Enter valid choice!!!");
                }
                System.out.println();
            }
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner, PreparedStatement preparedStatement){

                System.out.print("Enter Patient Id: ");
                int patientId = scanner.nextInt();
                System.out.print("Enter Doctor Id: ");
                int doctorId = scanner.nextInt();
                System.out.print("Enter appointment date (YYYY-MM-DD): ");
                String appointmentDate = scanner.next();

        if(!(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))) {
            System.out.println("Either doctor or patient doesn't exist!!!");
            return;
        }
        if(!checkDoctorAvailability(doctorId, appointmentDate, connection, preparedStatement)){
            System.out.println("Doctor not available on this date!!");
            return;
        }

        String appointmentQuery = BasicSQL.APPOINTMENT;
        try{
            preparedStatement = connection.prepareStatement(appointmentQuery);
            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, doctorId);
            preparedStatement.setString(3, appointmentDate);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Appointment Booked!" : "Failed to Book Appointment!");
        }catch (SQLException e){
                e.printStackTrace();
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection, PreparedStatement preparedStatement){
        String query = BasicSQL.DOCTOR_AVAILABILITY;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();

//            if(resultSet.next()){
//                int count = resultSet.getInt(1);
//                if(count!=0){
//                    return false;
//                }
//            }
            //Return False if either of the conditions is wrong
            return !resultSet.next() || resultSet.getInt(1) == 0;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
