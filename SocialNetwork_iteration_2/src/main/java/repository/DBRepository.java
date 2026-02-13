package repository;

import domain.Post;
import domain.Topic;
import domain.User;

import java.sql.*;
import java.util.ArrayList;

public class DBRepository {
    String URL = "jdbc:sqlite:D:\\facultate\\second_year\\tehnici avansate de programare\\exams\\retake_prep\\exam_test\\src\\main\\resources\\database.db";
    private Connection connection;
    static int postId = 2;

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

    public ArrayList<User> getAllUsers() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getString("name")));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
    public ArrayList<Topic> getAllTopics() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from topics");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Topic> topics = new ArrayList<>();
            while (rs.next()) {
                topics.add(new Topic(rs.getString("topic"), rs.getString("userName")));
            }
            return topics;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Post> getAllPostsDesc() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from posts order by [time] desc");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Post> posts = new ArrayList<>();
            while (rs.next()) {
                posts.add(new Post(
                        rs.getInt("id"), rs.getString("text"), rs.getString("time"), rs.getString("userName")
                ));
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addPost(String text, String userName) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into posts values (?, ?, ?, ?)");
            postId++;
            preparedStatement.setInt(1, postId);
            preparedStatement.setString(2, text);
            preparedStatement.setString(3, "2026-02-13");
            preparedStatement.setString(4, userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addTopic(String topic, String userName) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into topics values (?, ?)");
            preparedStatement.setString(1, topic);
            preparedStatement.setString(2, userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
    public void modifyPost(int id, String text) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update posts set text = ? where id = ?");
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}

