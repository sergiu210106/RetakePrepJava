package ui;

import domain.Journalist;
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
        for (Journalist journalist : service.getAllJournalists()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/WindowView.fxml"));
                Parent root = fxmlLoader.load();
                WindowController userController = fxmlLoader.getController();
                userController.setService(service, journalist);

                Stage stage = new Stage();
                stage.setTitle("User " + journalist.getName());
                stage.setScene(new Scene(root, 600, 1000));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
