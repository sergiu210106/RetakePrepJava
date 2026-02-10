package repository;

import domain.Post;
import domain.Topic;
import domain.User;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DBRepository {
    String URL = "jdbc:sqlite:D:/facultate/second_year/tehnici avansate de programare/exams/retake_prep/SocialNetwork/src/main/resources/database.db";

    private Connection connection = null;
    private static int postId = 5;

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

    public ArrayList<Post> getFeed(String userName) {
        ArrayList<Topic> topics = this.getTopics().stream().filter(t -> t.getUserName().equals(userName))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Post> posts = this.getAllPostsDesc();

        ArrayList<Post> feed = new ArrayList<>();
        for (Post p : posts) {
            boolean ok = false;
            if (p.getText().contains("@" + userName)) {
                ok = true;
            }

            for (Topic topic : topics) {
                if(p.getText().contains("#" + topic.getTopic())) {
                    ok = true;
                    break;
                }
            }

            if (ok) {
                feed.add(p);
            }
        }
        return feed;
    }

    public ArrayList<Topic> getTopics() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Topics");

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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Posts order by dateTime desc");

            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Post> posts = new ArrayList<>();
            while(rs.next()) {
                posts.add(new Post(
                        rs.getInt("id"), rs.getString("text"), rs.getString("dateTime"), rs.getString("userName")
                ));
            }

            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addUserTopic(String userName, String topic) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Topics values (?, ?)");
            preparedStatement.setString(1, topic);
            preparedStatement.setString(2, userName);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void addPost(String text, String dateTime, String userName) {
        if (text.length() <= 3) {
            throw new RuntimeException("text less than 3 characters");
        }

        postId++;
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Posts values (?, ?, ?, ?)");
            preparedStatement.setInt(1, postId);
            preparedStatement.setString(2, text);
            preparedStatement.setString(3, dateTime);
            preparedStatement.setString(4, userName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(1);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Post> getUserPosts(String userName) {
        return this.getAllPostsDesc().stream().
                filter(p -> p.getUserName().equals(userName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<User> getUsers() {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Users");
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<User> users = new ArrayList<>();
            while(rs.next()) {
                users.add(new User(rs.getString("name")));
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public void modifyPostText(int id, String text) {
        openConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Posts SET [text] = ? WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
