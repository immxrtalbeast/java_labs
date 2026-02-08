package sem2_1.task4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Task4App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Task4App.class.getResource("/task4/task4-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1150, 780);
        stage.setTitle("Task 4 - Function Charts (FXML)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
