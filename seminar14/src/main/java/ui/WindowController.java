package ui;

import domain.Client;
import domain.Observer;
import domain.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.Service;

public class WindowController implements Observer {
    private Service service;
    private Client client;

    @FXML private ListView<Room> rooms;
    private final ObservableList<Room> rooms_obs = FXCollections.observableArrayList();

    @FXML
    TextArea selectText;

    @FXML TextField typeText;
    @FXML TextField startText;
    @FXML TextField endText;

    public void setService(Service service, Client client) {
        this.service = service;
        this.client = client;
        this.service.addObserver(this);

        rooms.setItems(rooms_obs);

        update();

        rooms.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectText.setText(newVal.getDescription());
        });
    }

    @Override public void update() {
        rooms_obs.setAll(service.getRooms());
    }

    @FXML
    private void handleAdd() {
        try {
            float price = service.addBooking(client.getId(), startText.getText(), endText.getText(), typeText.getText());
            service.notifyObservers();
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Price: " + price);
            a.show();
        } catch (RuntimeException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
        }
    }

}
