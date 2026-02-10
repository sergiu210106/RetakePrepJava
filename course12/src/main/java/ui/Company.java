package ui;

import domain.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Service;

import domain.Package;

public class Company implements Observer {
    private Service service;
    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);

        packages.setItems(packages_obs);
        update();
    }

    @FXML
    private ListView<Package> packages;
    ObservableList<Package> packages_obs = FXCollections.observableArrayList();


    public void update() {
        packages_obs.setAll(service.getAllPackages());
    }

    @FXML private TextField recipient;
    @FXML private TextField address;
    @FXML private TextField x;
    @FXML private TextField y;

    @FXML private void handleAdd() {
        service.addPackage(
                recipient.getText(), address.getText(), Float.parseFloat(x.getText()), Float.parseFloat(y.getText())
        );
    }

}
