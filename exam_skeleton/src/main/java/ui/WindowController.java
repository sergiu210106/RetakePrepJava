package ui;

import domain.*;
import service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class WindowController implements Observer {
    private Service service;

    //@FXML
    //private ListView<Note> feed;
    //private ObservableList<Note> feed_obs = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);


        //feed.setItems(feed_obs);

        update();
    }

    @Override public void update() {
        //feed_obs.setAll(service.getFeed(userName));
    }
}
