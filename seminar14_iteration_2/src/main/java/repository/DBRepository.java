package repository;

import domain.Booking;
import domain.Client;
import domain.Room;

import java.awt.print.Book;
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

    public ArrayList<Client> getClients() {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from clients");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Client> clients = new ArrayList<>();
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"), rs.getString("name"), rs.getString("email")
                        ));
            }
            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Room> getRooms() {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from rooms");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Room> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("number"), rs.getString("type"),
                        rs.getString("description"), rs.getFloat("price")));
            }
            return rooms;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Booking> getBookings() {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from bookings order by start desc");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                bookings.add(new Booking(rs.getInt("clientId"), rs.getInt("roomNumber"),
                        rs.getString("start"), rs.getString("end")));
            }
            return bookings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addBooking(int clientId, int roomNumber, String start, String end) {
        openConnection();

        try  {
            System.out.println("1");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into bookings values (?,?,?,?)");
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.setString(3, start);
            preparedStatement.setString(4, end);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
