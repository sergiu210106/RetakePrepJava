package repository;
import domain.*;
import java.sql.*;
import java.util.ArrayList;

public class DBRepository {
    String URL = "";
    private Connection connection;

    private void openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public ArrayList<Package> getPackages() {
//        openConnection();
//
//        try  {
//            PreparedStatement preparedStatement = connection.prepareStatement("select * from packages");
//            ResultSet rs = preparedStatement.executeQuery();
//
//            ArrayList<Package> packages = new ArrayList<>();
//            while (rs.next()) {
//                packages.add(new Package(
//                        rs.getString("recipient"), rs.getString("address"), rs.getFloat("x"), rs.getFloat("y"), rs.getInt("status") != 0
//                ));
//            }
//            return packages;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            closeConnection();
//        }
//    }
//
//    public void insertPackage(String recipient, String address, float x, float y) {
//        openConnection();
//
//        try  {
//            PreparedStatement preparedStatement = connection.prepareStatement("insert into packages values (?, ?, ?, ?, 0)");
//            preparedStatement.setString(1, recipient);
//            preparedStatement.setString(2, address);
//            preparedStatement.setFloat(3, x);
//            preparedStatement.setFloat(4, y);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            closeConnection();
//        }
//    }
}
