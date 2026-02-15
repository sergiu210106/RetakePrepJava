package ui;

import domain.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class WindowController implements Observer {
    private Service service;
    private Journalist journalist;

    @FXML
    private ListView<Note> feed;
    private ObservableList<Note> feed_obs = FXCollections.observableArrayList();

    @FXML
    private ListView<Note> journalistNotes;
    private ObservableList<Note> journalistNotes_obs = FXCollections.observableArrayList();

    @FXML
    private ListView<Keyword> subscriptions;
    private ObservableList<Keyword> subscriptions_obs = FXCollections.observableArrayList();

    @FXML
    private ListView<Keyword> keywords;
    private ObservableList<Keyword> keywords_obs = FXCollections.observableArrayList();

    @FXML private TextField search;

    public void setService(Service service, Journalist journalist) {
        this.service = service;
        this.journalist = journalist;
        this.service.addObserver(this);


        feed.setItems(feed_obs);
        journalistNotes.setItems(journalistNotes_obs);
        subscriptions.setItems(subscriptions_obs);
        keywords.setItems(keywords_obs);

        update();
    }

    @Override public void update() {
        feed_obs.setAll(service.getFeed(journalist));
        journalistNotes_obs.setAll(service.getJournalistNotes(journalist));
        subscriptions_obs.setAll(service.getJournalistKeywords(journalist));
        keywords_obs.setAll(service.getAllKeywords());
    }

    @FXML public void handleSearch(KeyEvent event) {
        keywords_obs.setAll(service.filterKeywords(search.getText()));
    }

    @FXML public void handleSubscribe() {
        Keyword keyword = keywords.getSelectionModel().getSelectedItem();
        service.addKeyword(keyword, journalist);
        update();
    }
    @FXML private TextField writeTF;
    @FXML public void handlePost() {
        String text = writeTF.getText();

        if (text.length() <= 3) {
            Alert a =  new Alert(Alert.AlertType.ERROR, "text must be longer than 3 characters");
            return;
        }

        service.addNote(text, "2026-02-15", journalist.getName());
        service.notifyObservers();
    }

    @FXML public void handleModify() {
        Note note = journalistNotes.getSelectionModel().getSelectedItem();
        String text = writeTF.getText();

        if (text.length() <= 3) {
            Alert a =  new Alert(Alert.AlertType.ERROR, "text must be longer than 3 characters");
            return;
        }

        if (note != null) {
            service.modifyNote(note, text);
            service.notifyObservers();
        }

    }

}
