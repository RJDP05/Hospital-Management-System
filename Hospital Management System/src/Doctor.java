import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public Doctor(Connection connection, PreparedStatement preparedStatement) {
        this.connection = connection;
        this.preparedStatement = preparedStatement;
    }

    public void viewDoctors() {
        String query = BasicSQL.VIEW_DOCTORS;
        try{
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+----------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization       |");
            System.out.println("+------------+--------------------+----------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("idDoctor");
                String name = resultSet.getString("Name");
                String specialization = resultSet.getString("Specialization");
                System.out.printf("| %-10s | %-18s | %-20s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+----------------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = BasicSQL.GET_DOCTOR_ID;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
//            if(!resultSet.next()){
//                return false;
//            }
            return resultSet.next();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

}

