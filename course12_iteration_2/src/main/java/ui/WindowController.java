package ui;

import domain.Courier;
import domain.Observer;
import domain.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import service.Service;

public class WindowController implements Observer {
    private Service service;
    private Courier courier;


    @FXML
    private ListView<Package> feed;
    private ObservableList<Package> feed_obs = FXCollections.observableArrayList();

    @FXML
    private ListView<Package> optimise;
    private ObservableList<Package> optimise_obs = FXCollections.observableArrayList();

    @FXML
    TextArea zoneTA;

    public void setService(Service service, Courier courier) {
        this.service = service;
        this.courier = courier;
        this.service.addObserver(this);


        feed.setItems(feed_obs);
        optimise.setItems(optimise_obs);
        cb.setItems(cb_obs);

        update();
    }

    @Override public void update() {
        feed_obs.setAll(service.getFeed(courier));
        zoneTA.setText("streets:" + courier.getListStreets() + "\n" + "x: " + courier.getCenterX() + "; y:" + courier.getCenterY() + "; r:" + courier.getRadius()) ;
        optimise_obs.setAll(service.getOptimise(courier));
        cb_obs.setAll(service.getStreets(courier));
    }

    @FXML private void handleOptimise() {
        optimise_obs.setAll(service.getOptimise(courier));
    }

    @FXML
    ComboBox<String> cb;
    private ObservableList<String> cb_obs = FXCollections.observableArrayList();

    @FXML private void handleSelectCB() {
        String street = cb.getValue();
        feed_obs.setAll(service.filterPackages(street, courier));
    }

    @FXML private void handleDeliver() {
        Package p = feed.getSelectionModel().getSelectedItem();
        if (p != null) {
            service.deliver(p);
            service.notifyObservers();
        }
    }

}
