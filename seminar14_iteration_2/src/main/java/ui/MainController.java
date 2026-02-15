package ui;

import domain.Client;
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
        launchStaff();
    }

    private void launchWindows() {
        for (Client client : service.getAllClients()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/WindowView.fxml"));
                Parent root = fxmlLoader.load();
                WindowController userController = fxmlLoader.getController();
                userController.setService(service, client);

                Stage stage = new Stage();
                stage.setTitle("Client " + client.getName());
                stage.setScene(new Scene(root, 600, 1000));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void launchStaff() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/StaffView.fxml"));
            Parent root = fxmlLoader.load();
            StaffController userController = fxmlLoader.getController();
            userController.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Staff");
            stage.setScene(new Scene(root, 600, 1000));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
