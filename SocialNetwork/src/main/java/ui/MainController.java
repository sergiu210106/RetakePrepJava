package ui;

import domain.User;
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
    }

    private void launchWindows() {
        for (User user : service.getUsers()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/UserController.fxml"));
                Parent root = fxmlLoader.load();
                UserController userController = fxmlLoader.getController();
                userController.setService(service, user.getName());

                Stage stage = new Stage();
                stage.setTitle("User " + user.getName());
                stage.setScene(new Scene(root, 600, 1000));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
