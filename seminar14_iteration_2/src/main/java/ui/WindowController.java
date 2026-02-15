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

    @FXML
    private ListView<Room> rooms;
    private ObservableList<Room> rooms_obs = FXCollections.observableArrayList();

    @FXML
    TextArea descTA;

    public void setService(Service service, Client client) {
        this.service = service;
        this.client = client;
        this.service.addObserver(this);


        rooms.setItems(rooms_obs);

        rooms.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            descTA.setText(newVal.getDescription());
        });

        update();
    }

    @Override public void update() {
        rooms_obs.setAll(service.getAllRooms());
    }


    @FXML
    TextField start;
    @FXML
    TextField end;
    @FXML
    TextField type;

    @FXML private void addBooking() {
        try {
            System.out.println(1);
            float finalPrice = service.addBooking(client, start.getText(), end.getText(), type.getText());
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Final price: " + finalPrice);
            a.show();
        } catch (RuntimeException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
        }

        service.notifyObservers();
    }
}
