package ui;

import domain.Courier;
import domain.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;
import service.Service;
import domain.Package;

public class CourierController implements Observer {
    private Service service;
    private Courier courier;
    @FXML
    TextArea zoneText;

    @FXML private ListView<Package> packages;
    private final ObservableList<Package> packages_obs = FXCollections.observableArrayList();

    @FXML private ComboBox<String> cb;
    private final ObservableList<String> cb_obs = FXCollections.observableArrayList();
    @FXML private TextArea streetsTA;


    public void setService(Service service, Courier courier) {
        this.service = service;
        this.courier = courier;
        this.service.addObserver(this);

        packages.setItems(packages_obs);
        cb.setItems(cb_obs);
        update();
    }

    public void update() {
        System.out.println(courier);
        zoneText.setText("X: " + String.valueOf(courier.getCentreX()) + ", Y: " + String.valueOf(courier.getCentreY()) + ", Radius: " + String.valueOf(courier.getRadius()));
        packages_obs.setAll(service.getPackages(courier));
        cb_obs.setAll(service.getStreets(courier));
        streetsTA.setText(service.getStreetPackages(cb.getValue()));
    }

    @FXML private void handleDeliver() {
        Package p = packages.getSelectionModel().getSelectedItem();
        System.out.println(p);
        service.deliver(p);
        service.notifyObservers();
    }
}
