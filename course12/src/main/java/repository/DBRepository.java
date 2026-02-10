package repository;

import domain.Courier;
import domain.Package;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBRepository {
    String URL = "jdbc:sqlite:D:\\facultate\\second_year\\tehnici avansate de programare\\exams\\retake_prep\\course12\\src\\main\\resources\\database.db";
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

    public ArrayList<Courier> getAllCouriers() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Couriers");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Courier> couriers = new ArrayList<>();
            while (rs.next()) {
                couriers.add(new Courier(
                   rs.getString("name"), rs.getString("listOfStreets"), rs.getFloat("centreX"), rs.getFloat("centreY"), rs.getFloat("radius")
                ));
            }
            return couriers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Package> getPackages() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Packages");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Package> packages = new ArrayList<>();
            while(rs.next()) {
                packages.add(new Package(
                        rs.getString("recipient"), rs.getString("address"), rs.getFloat("x"), rs.getFloat("y"),
                        rs.getInt("status") != 0
                ));
            }
            return packages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addPackage(String recipient, String address, float x, float y) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Packages values (?, ?, ?, ?, 0)");
            preparedStatement.setString(1, recipient);
            preparedStatement.setString(2, address);
            preparedStatement.setFloat(3, x);
            preparedStatement.setFloat(4, y);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void deliver(String recipient, String address) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update Packages set status = 1 where recipient = ? and address = ?");
            preparedStatement.setString(1,  recipient);
            preparedStatement.setString(2,  address);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
