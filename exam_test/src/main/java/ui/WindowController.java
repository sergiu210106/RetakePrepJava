package ui;

import domain.Journalist;
import domain.Note;
import domain.Observer;
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
    private Journalist journalist;

    @FXML
    private ListView<Note> feed;
    private ObservableList<Note> feed_obs = FXCollections.observableArrayList();

    @FXML private ListView<Note> myNotes;
    private ObservableList<Note> myNotes_obs = FXCollections.observableArrayList();

    @FXML private ListView<String> keywords;
    private ObservableList<String> keywords_obs = FXCollections.observableArrayList();

    @FXML private ListView<String> myKeywords;
    private ObservableList<String> myKeywords_obs = FXCollections.observableArrayList();

    public void setService(Service service, Journalist journalist) {
        this.service = service;
        this.journalist = journalist;
        this.service.addObserver(this);

        feed.setItems(feed_obs);
        myNotes.setItems(myNotes_obs);
        keywords.setItems(keywords_obs);
        myKeywords.setItems(myKeywords_obs);

        update();
    }

    @Override public void update() {
        feed_obs.setAll(service.getFeed(journalist));
        myNotes_obs.setAll(service.getNotes(journalist));
        myKeywords_obs.setAll(service.getKeywords(journalist));
        keywords_obs.setAll(service.getAllKeywords());
    }

    @FXML
    TextField search;

    @FXML public void handleFilter(KeyEvent event) {
        keywords_obs.setAll(service.filterKeywords(search.getText(), journalist));
    }

    @FXML public void handleAddKeyword() {
        String keyword = keywords.getSelectionModel().getSelectedItem();
        if (keyword != null) {
            service.addKeyword(keyword, journalist.getName());
        }
        update();
    }

    @FXML TextField noteText;
    @FXML public void handleAddNote() {
        String text = noteText.getText();
        if (text.length() <= 3) {
            Alert a = new Alert(Alert.AlertType.ERROR, "text must have more than 3 characters");
            a.show();
            return;
        }

        service.addNote(text, journalist.getName());
        service.notifyObservers();
    }

    @FXML public void handleEdit() {
        String text = noteText.getText();
        if (text.length() <= 3) {
            Alert a = new Alert(Alert.AlertType.ERROR, "text must have more than 3 characters");
            a.show();
            return;
        }

        Note selected = myNotes.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.modifyNote(selected.getId(), text);
        }
        service.notifyObservers();
    }




}
