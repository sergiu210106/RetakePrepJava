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
    private final ObservableList<Room> rooms_obs = FXCollections.observableArrayList();
    @FXML
    private ListView<Booking> bookings;
    private final ObservableList<Booking> bookings_obs = FXCollections.observableArrayList();

    @FXML
    TextArea priceTA;


    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);

        rooms.setItems(rooms_obs);
        bookings.setItems(bookings_obs);

        update();

        rooms.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            bookingsTA.setText(service.getSortedBookings(newVal.getNumber()));
        });
    }

    @FXML TextArea bookingsTA;

    @Override
    public void update() {
        rooms_obs.setAll(service.getRooms());
        bookings_obs.setAll(service.getBookings());
        priceTA.setText(String.valueOf(service.getTotalPrice()));
    }
}
