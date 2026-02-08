package sem2_1.task7;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Task7App extends Application {
    private MediaPlayer mediaPlayer;
    private final MediaView mediaView = new MediaView();
    private Timeline progressTimeline;

    private final Label fileLabel = new Label("Файл: не выбран");
    private final Label totalLabel = new Label("Общая: 00:00");
    private final Label currentLabel = new Label("Текущая: 00:00");
    private final Slider volumeSlider = new Slider(0, 1, 0.5);

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(8));

        Button openButton = new Button("Открыть...");
        openButton.setOnAction(event -> openMedia(stage));

        HBox infoBar = new HBox(10, openButton, fileLabel, totalLabel, currentLabel);
        HBox.setHgrow(fileLabel, Priority.ALWAYS);
        infoBar.setPadding(new Insets(0, 0, 8, 0));

        StackPane centerPane = new StackPane(mediaView);
        centerPane.setStyle("-fx-background-color: black;");

        Button playButton = new Button("Play / Resume");
        playButton.setOnAction(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.play();
            }
        });
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        });
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        });

        HBox controls = new HBox(10,
                playButton,
                pauseButton,
                stopButton,
                new Label("Volume"),
                volumeSlider
        );
        controls.setPadding(new Insets(8, 0, 0, 0));
        controls.setFillHeight(false);

        root.setTop(infoBar);
        root.setCenter(centerPane);
        root.setBottom(controls);

        Scene scene = new Scene(root, 1200, 780);
        stage.setTitle("Task 7 - Media Player");
        stage.setScene(scene);
        stage.show();

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });
    }

    private void openMedia(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Открыть медиафайл");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Media files", "*.mp4", "*.m4v", "*.mp3", "*.wav", "*.aac", "*.flv"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File file = chooser.showOpenDialog(stage);
        if (file == null) {
            return;
        }

        try {
            Media media = new Media(file.toURI().toString());
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volumeSlider.getValue());
            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnReady(() -> totalLabel.setText("Общая: " + formatDuration(mediaPlayer.getTotalDuration())));
            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) ->
                    currentLabel.setText("Текущая: " + formatDuration(newValue)));

            if (progressTimeline != null) {
                progressTimeline.stop();
            }
            progressTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (mediaPlayer != null) {
                    currentLabel.setText("Текущая: " + formatDuration(mediaPlayer.getCurrentTime()));
                }
            }));
            progressTimeline.setCycleCount(Timeline.INDEFINITE);
            progressTimeline.play();

            fileLabel.setText("Файл: " + file.getName());
            mediaPlayer.play();
        } catch (Exception exception) {
            new Alert(Alert.AlertType.ERROR, "Не удалось открыть медиафайл: " + exception.getMessage()).showAndWait();
        }
    }

    private String formatDuration(Duration duration) {
        if (duration == null || duration.isUnknown()) {
            return "00:00";
        }
        int totalSeconds = (int) Math.floor(duration.toSeconds());
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void stop() {
        if (progressTimeline != null) {
            progressTimeline.stop();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
