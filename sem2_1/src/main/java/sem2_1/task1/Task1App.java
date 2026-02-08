package sem2_1.task1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sem2_1.common.SnapshotSaver;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Task1App extends Application {
    private static final double ICON_SIZE = 64;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(8));

        Pane drawPane = new Pane();
        drawPane.setStyle("-fx-background-color: white; -fx-border-color: #cfcfcf;");
        drawPane.setPrefSize(900, 700);

        Map<ToggleButton, Image> imageButtons = createImageButtons();
        ToggleGroup toggleGroup = new ToggleGroup();
        VBox toolbar = new VBox(8);
        toolbar.setPadding(new Insets(8));
        toolbar.setAlignment(Pos.TOP_CENTER);
        for (ToggleButton button : imageButtons.keySet()) {
            button.setToggleGroup(toggleGroup);
            toolbar.getChildren().add(button);
        }

        Button saveButton = new Button("Сохранить");
        saveButton.setOnAction(event -> saveImage(stage, drawPane));
        VBox topBox = new VBox(saveButton);
        topBox.setPadding(new Insets(0, 0, 8, 0));

        drawPane.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.SECONDARY) {
                return;
            }
            ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
            if (selectedButton == null) {
                return;
            }
            Image image = imageButtons.get(selectedButton);
            if (image == null) {
                return;
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(ICON_SIZE);
            imageView.setFitHeight(ICON_SIZE);
            imageView.setPreserveRatio(true);
            imageView.setLayoutX(event.getX() - ICON_SIZE / 2.0);
            imageView.setLayoutY(event.getY() - ICON_SIZE / 2.0);
            drawPane.getChildren().add(imageView);
        });

        root.setTop(topBox);
        root.setLeft(toolbar);
        root.setCenter(drawPane);

        Scene scene = new Scene(root, 1100, 760);
        stage.setTitle("Task 1 - Composite Image");
        stage.setScene(scene);
        stage.show();
    }

    private Map<ToggleButton, Image> createImageButtons() {
        Map<ToggleButton, Image> result = new LinkedHashMap<>();
        result.put(createImageToggle("/task1/icons/red.png"), loadImage("/task1/icons/red.png"));
        result.put(createImageToggle("/task1/icons/green.png"), loadImage("/task1/icons/green.png"));
        result.put(createImageToggle("/task1/icons/blue.png"), loadImage("/task1/icons/blue.png"));
        return result;
    }

    private ToggleButton createImageToggle(String path) {
        Image image = loadImage(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(ICON_SIZE);
        imageView.setFitHeight(ICON_SIZE);
        imageView.setPreserveRatio(true);
        ToggleButton button = new ToggleButton();
        button.setGraphic(imageView);
        button.setMinSize(ICON_SIZE + 10, ICON_SIZE + 10);
        return button;
    }

    private Image loadImage(String path) {
        return new Image(Task1App.class.getResourceAsStream(path));
    }

    private void saveImage(Stage stage, Pane drawPane) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Сохранить изображение");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        chooser.setInitialFileName("task1-image.png");
        File file = chooser.showSaveDialog(stage);
        if (file == null) {
            return;
        }

        try {
            SnapshotSaver.save(drawPane, file);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось сохранить файл: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
