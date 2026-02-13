package ui;

import domain.Observer;
import domain.Post;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import service.Service;

public class WindowController implements Observer {
    private Service service;
    private User user;

    @FXML
    private ListView<Post> feed;
    private ObservableList<Post> feed_obs = FXCollections.observableArrayList();

    @FXML
private ListView<Post> myPosts;
    private ObservableList<Post> myPosts_obs = FXCollections.observableArrayList();

    @FXML
    private ListView<String> subscriptions;
    private ObservableList<String> subscriptions_obs = FXCollections.observableArrayList();

    @FXML
    private ListView<String> topicSearch;
    private ObservableList<String> topicSearch_obs = FXCollections.observableArrayList();

    @FXML
    TextField searchTF;

    public void setService(Service service, User user) {
        this.service = service;
        this.user = user;

        this.service.addObserver(this);

        feed.setItems(feed_obs);
        myPosts.setItems(myPosts_obs);
        subscriptions.setItems(subscriptions_obs);
        topicSearch.setItems(topicSearch_obs);

        update();
    }

    @Override public void update() {
        feed_obs.setAll(service.getFeed(user));
        myPosts_obs.setAll(service.getPosts(user));
        subscriptions_obs.setAll(service.getTopics(user));
        topicSearch_obs.setAll(service.getAllTopics());
    }

    @FXML private void handleSearch(KeyEvent event) {
        topicSearch_obs.setAll(service.filter(searchTF.getText()));
    }

    @FXML private void handleAddTopic() {
        String topic = topicSearch.getSelectionModel().getSelectedItem();
        service.addTopic(topic, user.getName());
        update();
    }

    @FXML TextField addPostTF;
    @FXML private void handleAddPost() {
        if (addPostTF.getText().length() <= 3) {
            Alert a = new Alert(Alert.AlertType.ERROR, "post must have more than 3 characters");
            a.show();
        }

        service.addPost(addPostTF.getText(), user);
        service.notifyObservers();
    }

    @FXML private void handleModify() {
        if (addPostTF.getText().length() <= 3) {
            Alert a = new Alert(Alert.AlertType.ERROR, "post must have more than 3 characters");
            a.show();
        }
        Post p = myPosts.getSelectionModel().getSelectedItem();
        if (p != null) {
            service.modifyPost(p.getId(), addPostTF.getText());
            service.notifyObservers();
        }
    }
}
