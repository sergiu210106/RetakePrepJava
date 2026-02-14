package ui;

import domain.Courier;
import domain.Observer;
import domain.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Service;

public class Company implements Observer {
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
        packages_obs.setAll(service.getPackages());
    }

    @FXML
    TextField recipient;
    @FXML
    TextField address;
    @FXML
    TextField x;
    @FXML
    TextField y;

    @FXML private void handleAdd() {
        service.addPackage(recipient.getText(), address.getText(), x.getText(), y.getText());
        service.notifyObservers();
    }
}
