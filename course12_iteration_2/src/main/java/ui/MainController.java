package ui;

import domain.Courier;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import service.Service;

import java.io.IOException;

public class MainController {
    private Service service;

    public void setService(Service service) {
        this.service = service;
        launchWindows();
        launchCompany();
        launchMap();
    }

    private void launchWindows() {
        for (Courier courier : service.getCouriers()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/WindowView.fxml"));
                Parent root = fxmlLoader.load();
                WindowController userController = fxmlLoader.getController();
                userController.setService(service, courier);

                Stage stage = new Stage();
                stage.setTitle("User " + courier.getName());
                stage.setScene(new Scene(root, 600, 1000));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void launchCompany() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/Company.fxml"));
            Parent root = fxmlLoader.load();
            Company userController = fxmlLoader.getController();
            userController.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Company");
            stage.setScene(new Scene(root, 600, 1000));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void launchMap() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/Map.fxml"));
            Parent root = fxmlLoader.load();
            Map userController = fxmlLoader.getController();
            userController.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Map");
            stage.setScene(new Scene(root, 600, 1000));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
