import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    private PreparedStatement preparedStatement;

    public Patient(Connection connection, Scanner scanner,  PreparedStatement preparedStatement) {
        this.scanner = scanner;
        this.connection = connection;
        this.preparedStatement = preparedStatement;
    }


    public void addPatient() {
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();

        try{
            String query = BasicSQL.ADD_PATIENTS;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
//            if(affectedRows>0){
//                System.out.println("Patient Added Successfully!!");
//            }else{
//                System.out.println("Failed to add Patient!!");
//            }
            System.out.println(affectedRows > 0 ? "Patient Added Successfully!!" : "Failed to add Patient!!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = BasicSQL.VIEW_PATIENTS;
        try{
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("idPatient");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String gender = resultSet.getString("Gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = BasicSQL.GET_PATIENT_ID;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Return False
            return resultSet.next();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

}

