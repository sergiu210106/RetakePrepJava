package repository;

import domain.Courier;
import domain.Package;
import jdk.jshell.spi.SPIResolutionException;

import java.lang.module.ResolutionException;
import java.sql.*;
import java.util.ArrayList;

public class DBRepository {
    String URL = "jdbc:sqlite:D:\\facultate\\second_year\\tehnici avansate de programare\\exams\\retake_prep\\course12_iteration_2\\src\\main\\resources\\database.db";
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

    public ArrayList<Courier> getCouriers() {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from couriers");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Courier> couriers = new ArrayList<>();
            while (rs.next()) {
                couriers.add(new Courier(
                        rs.getString("name"), rs.getString("listStreets"), rs.getFloat("centerX"), rs.getFloat("centerY"), rs.getFloat("radius")
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

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from packages");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Package> packages = new ArrayList<>();
            while (rs.next()) {
                packages.add(new Package(
                        rs.getString("recipient"), rs.getString("address"), rs.getFloat("x"), rs.getFloat("y"), rs.getInt("status") != 0
                ));
            }
            return packages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void insertPackage(String recipient, String address, float x, float y) {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into packages values (?, ?, ?, ?, 0)");
            preparedStatement.setString(1, recipient);
            preparedStatement.setString(2, address);
            preparedStatement.setFloat(3, x);
            preparedStatement.setFloat(4, y);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void deliver(String recipient) {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("update packages set status = 1 where recipient = ?");
            preparedStatement.setString(1, recipient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

}


