package ui;

import domain.Booking;
import domain.Observer;
import domain.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import service.Service;

public class StaffController implements Observer {
    private Service service;

    @FXML
    private ListView<Room> rooms;
    private ObservableList<Room> rooms_obs = FXCollections.observableArrayList();

    @FXML
    private ListView<Booking> bookings;
    private ObservableList<Booking> bookings_obs = FXCollections.observableArrayList();

    @FXML
    private ListView<Booking> roomBookings;
    private ObservableList<Booking> roomBookings_obs = FXCollections.observableArrayList();

    @FXML private TextArea totalTA;

    public void setService(Service service) {
        this.service = service;
        rooms.setItems(rooms_obs);
        bookings.setItems(bookings_obs);
        roomBookings.setItems(roomBookings_obs);
        update();

        rooms.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            roomBookings_obs.setAll(service.getRoomBookings(newVal));
        });
    }

    @Override
    public void update() {
        rooms_obs.setAll(service.getAllRooms());
        bookings_obs.setAll(service.getAllBookings());
        totalTA.setText("Total" + service.getTotalPrice());
    }


}
