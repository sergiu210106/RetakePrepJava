package repository;
import domain.*;
import java.sql.*;
import java.util.ArrayList;

public class DBRepository {
    String URL = "jdbc:sqlite:D:\\facultate\\second_year\\tehnici avansate de programare\\exams\\retake_prep\\exam_model_iteration_2\\src\\main\\resources\\database.db";
    private Connection connection;

    public static int postId = 2;

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

    public ArrayList<Journalist> getJournalists() {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from journalists");
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

    public ArrayList<Keyword> getKeywords() {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from keywords");
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

    public ArrayList<Note> getNotes() {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from notes order by [time] desc");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Note> notes = new ArrayList<>();
            while (rs.next()) {
                notes.add(new Note(rs.getInt("id"), rs.getString("text"), rs.getString("time"), rs.getString("jName")));
            }
            return notes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addKeyword(String keyword, String jName) {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into keywords values (?, ?)");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, jName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addNote(String text, String time, String jName) {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into notes values (?, ?, ?, ?)");
            postId++;
            preparedStatement.setInt(1, postId);
            preparedStatement.setString(2, text);
            preparedStatement.setString(3, time);
            preparedStatement.setString(4, jName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void modifyNote(int id, String text) {
        openConnection();

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement("update notes set text = ? where id = ?");
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, text);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }

    }

}
