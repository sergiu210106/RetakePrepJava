package ui;

import domain.Observer;
import domain.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import service.Service;

public class Map implements Observer {
    private Service service;

    @FXML
    private ListView<domain.Package> packages;
    private ObservableList<Package> packages_obs = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);

        packages.setItems(packages_obs);
        update();
    }

    @Override
    public void update() {
        packages_obs.setAll(service.getUndelivered());
    }
}
