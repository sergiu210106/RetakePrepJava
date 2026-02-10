package ui;

import domain.Observer;
import domain.Post;
import domain.Topic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import service.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserController implements Observer {
    private Service service;
    private String userName;

    @FXML private ListView<Post> feed;
    @FXML private ListView<Post> userPosts;
    @FXML private ListView<Topic> subscriptions;
    @FXML private ListView<Topic> topics;

    private final ObservableList<Post> feed_obs = FXCollections.observableArrayList();
    private final ObservableList<Post> userPosts_obs = FXCollections.observableArrayList();
    private final ObservableList<Topic> subscriptions_obs = FXCollections.observableArrayList();
    private final ObservableList<Topic> topics_obs = FXCollections.observableArrayList();

    @FXML private TextField filterField;

    public void setService(Service service, String userName) {
        this.service = service;
        this.userName = userName;
        this.service.addObserver(this);

        feed.setItems(feed_obs);
        userPosts.setItems(userPosts_obs);
        subscriptions.setItems(subscriptions_obs);
        topics.setItems(topics_obs);

        // Load the data
        loadData();
    }

    private void loadData() {
        feed_obs.setAll(service.getFeed(userName));
        userPosts_obs.setAll(service.getUserPosts(userName));
        subscriptions_obs.setAll(service.getSubscriptions(userName));
        topics_obs.setAll(service.getTopics());
    }

    @FXML private void handleAdd() {
        Topic topic = topics.getSelectionModel().getSelectedItem();
        service.addUserTopic(userName, topic.getTopic());
        loadData();
    }
    @FXML void handleFilter(KeyEvent event) {
        String filter = filterField.getText();
        topics_obs.setAll(service.getTopics()
                .stream().filter(t->t.getTopic().contains(filter))
                .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    @FXML private TextField textTextField;
    @FXML private TextField dateTextField;

    @FXML void handleAddPost() {
        String text = textTextField.getText();
        String date = dateTextField.getText();
        try {

            service.addPost(text, date, userName);
        } catch (RuntimeException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML void handleModify() {
        String text = textTextField.getText();
        if (text.length() < 3) {
            Alert a = new Alert(Alert.AlertType.ERROR, "text less than 3 characters");
            return;
        }
        Post p = userPosts.getSelectionModel().getSelectedItem();
        service.modifyPostText(p.getId(), text);
    }

    @Override
    public void update() {
        loadData();
    }
}
