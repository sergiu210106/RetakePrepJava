package repository;

import domain.Journalist;
import domain.Keyword;
import domain.Note;

import java.sql.*;
import java.util.ArrayList;

public class DBRepository {
    String URL = "jdbc:sqlite:D:\\facultate\\second_year\\tehnici avansate de programare\\exams\\retake_prep\\exam_test\\src\\main\\resources\\database.db";
    private Connection connection;
    public static int postId = 3;

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

    public ArrayList<Note> getNotesDesc() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Notes order by [time] desc");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Note> notes = new ArrayList<>();
            while (rs.next()) {
                notes.add(
                        new Note(rs.getInt("id"), rs.getString("text"), rs.getString("time"), rs.getString("jName"))
                );
            }
            return notes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Keyword> getKeywords() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Keywords");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Keyword> keywords = new ArrayList<>();
            while (rs.next()) {
                keywords.add(new Keyword(rs.getString("keyword"), rs.getString("jName")));
            }
            return keywords;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Journalist> getJournalists() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Journalists");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Journalist> journalists = new ArrayList<>();
            while (rs.next()) {
                journalists.add(new Journalist(rs.getString("name")));
            }
            return journalists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addKeyword(String keyword, String name) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Keywords values (?, ?)");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addNote(String text, String name) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Notes values (?, ?, ?, ?)");
            postId++;
            preparedStatement.setInt(1, postId);
            preparedStatement.setString(2, text);
            preparedStatement.setString(3, "2026-02-12");
            preparedStatement.setString(4, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void modifyNote(int id, String newText) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update Notes set [text] = ? where id = ?");
            preparedStatement.setString(1, newText);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
