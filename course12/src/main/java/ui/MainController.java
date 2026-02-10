package ui;

import domain.Courier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class MainController {
    private Service service;
    public void setService(Service service) {
        this.service = service;
        launchWindows();
        launchCompany();
    }

    void launchWindows() {
        for (Courier courier : service.getCouriers()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/CourierController.fxml"));
                Parent root = fxmlLoader.load();
                CourierController courierController = fxmlLoader.getController();
                courierController.setService(service, courier);

                Stage stage = new Stage();
                stage.setTitle("Courier " + courier.getName());
                stage.setScene(new Scene(root, 1000, 1000));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void launchCompany() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/Company.fxml"));
            Parent root = fxmlLoader.load();
            Company company = fxmlLoader.getController();
            company.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Company");
            stage.setScene(new Scene(root, 1000, 1000));
            stage.show();

        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
    }
}
