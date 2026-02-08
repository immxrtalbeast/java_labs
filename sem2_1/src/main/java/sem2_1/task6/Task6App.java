package sem2_1.task6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sem2_1.common.SnapshotSaver;
import sem2_1.task6.io.XyzParser;
import sem2_1.task6.model.Atom;
import sem2_1.task6.model.Molecule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task6App extends Application {
    private static final double SPACE_SCALE = 90.0;
    private static final double BOND_THRESHOLD = 1.8;

    private final Group moleculeGroup = new Group();
    private final Group world = new Group(moleculeGroup);
    private final Scale scaleTransform = new Scale(1, 1, 1);
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private final Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
    private final XyzParser parser = new XyzParser();

    private final Map<String, Color> elementColors = new HashMap<>();
    private final Map<String, Double> elementRadii = new HashMap<>();

    private final Label infoLabel = new Label("Молекула не загружена");
    private final VBox colorsBox = new VBox(6);

    private Molecule currentMolecule;

    @Override
    public void start(Stage stage) {
        world.getTransforms().setAll(scaleTransform, rotateX, rotateY, rotateZ);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(8));

        SubScene subScene = new SubScene(world, 960, 720, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(10, 10, 22));
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(5000);
        camera.setTranslateZ(-1250);
        subScene.setCamera(camera);

        StackPane centerPane = new StackPane(subScene);
        centerPane.setStyle("-fx-background-color: black;");
        subScene.widthProperty().bind(centerPane.widthProperty());
        subScene.heightProperty().bind(centerPane.heightProperty());

        Button openButton = new Button("Открыть XYZ...");
        openButton.setOnAction(event -> openXyz(stage));

        Button saveButton = new Button("Сохранить картинку");
        saveButton.setOnAction(event -> saveSnapshot(stage, centerPane));

        HBox top = new HBox(10, openButton, saveButton, infoLabel);
        top.setPadding(new Insets(0, 0, 8, 0));
        HBox.setHgrow(infoLabel, Priority.ALWAYS);

        VBox rightControls = createRightPanel();

        root.setTop(top);
        root.setCenter(centerPane);
        root.setRight(rightControls);

        Scene scene = new Scene(root, 1320, 860, true);
        stage.setTitle("Task 6 - 3D Molecule Viewer");
        stage.setScene(scene);
        stage.show();

        loadBundledExample();
    }

    private VBox createRightPanel() {
        Slider scaleSlider = new Slider(0.3, 3.0, 1.0);
        scaleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            scaleTransform.setX(newValue.doubleValue());
            scaleTransform.setY(newValue.doubleValue());
            scaleTransform.setZ(newValue.doubleValue());
        });

        Slider rotateXSlider = new Slider(-180, 180, 0);
        rotateXSlider.valueProperty().addListener((observable, oldValue, newValue) -> rotateX.setAngle(newValue.doubleValue()));

        Slider rotateYSlider = new Slider(-180, 180, 0);
        rotateYSlider.valueProperty().addListener((observable, oldValue, newValue) -> rotateY.setAngle(newValue.doubleValue()));

        Slider rotateZSlider = new Slider(-180, 180, 0);
        rotateZSlider.valueProperty().addListener((observable, oldValue, newValue) -> rotateZ.setAngle(newValue.doubleValue()));

        VBox controls = new VBox(10,
                new Label("Управление молекулой"),
                new Label("Масштаб"),
                scaleSlider,
                new Label("Поворот X"),
                rotateXSlider,
                new Label("Поворот Y"),
                rotateYSlider,
                new Label("Поворот Z"),
                rotateZSlider,
                new Label("Цвета атомов"),
                colorsBox
        );
        controls.setPadding(new Insets(8));
        controls.setPrefWidth(300);
        return controls;
    }

    private void openXyz(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Открыть XYZ файл");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XYZ files", "*.xyz"));
        File file = chooser.showOpenDialog(stage);
        if (file == null) {
            return;
        }

        try {
            currentMolecule = parser.parse(file.toPath());
            renderMolecule();
        } catch (Exception exception) {
            new Alert(Alert.AlertType.ERROR, "Ошибка чтения XYZ: " + exception.getMessage()).showAndWait();
        }
    }

    private void saveSnapshot(Stage stage, StackPane centerPane) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Сохранить изображение");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        chooser.setInitialFileName("task6-molecule.png");
        File file = chooser.showSaveDialog(stage);
        if (file == null) {
            return;
        }
        try {
            SnapshotSaver.save(centerPane, file);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, "Не удалось сохранить изображение: " + exception.getMessage()).showAndWait();
        }
    }

    private void loadBundledExample() {
        try (InputStream inputStream = Task6App.class.getResourceAsStream("/task6/molecules/sample.xyz")) {
            if (inputStream == null) {
                return;
            }
            currentMolecule = parser.parse(inputStream);
            renderMolecule();
        } catch (Exception ignored) {
        }
    }

    private void renderMolecule() {
        if (currentMolecule == null) {
            return;
        }
        moleculeGroup.getChildren().clear();
        ensureElementStyles(currentMolecule);

        for (Atom atom : currentMolecule.getAtoms()) {
            moleculeGroup.getChildren().add(createAtomSphere(atom));
        }

        for (int i = 0; i < currentMolecule.getAtoms().size(); i++) {
            Atom first = currentMolecule.getAtoms().get(i);
            for (int j = i + 1; j < currentMolecule.getAtoms().size(); j++) {
                Atom second = currentMolecule.getAtoms().get(j);
                if (distance(first, second) <= BOND_THRESHOLD) {
                    moleculeGroup.getChildren().add(createBond(first, second));
                }
            }
        }

        infoLabel.setText("Описание: " + currentMolecule.getDescription()
                + " | Атомов: " + currentMolecule.getAtoms().size());
    }

    private void ensureElementStyles(Molecule molecule) {
        Set<String> elements = new HashSet<>();
        for (Atom atom : molecule.getAtoms()) {
            elements.add(atom.element());
            elementColors.putIfAbsent(atom.element(), defaultColor(atom.element()));
            elementRadii.putIfAbsent(atom.element(), defaultRadius(atom.element()));
        }

        colorsBox.getChildren().clear();
        for (String element : elements) {
            ColorPicker picker = new ColorPicker(elementColors.get(element));
            picker.setOnAction(event -> {
                elementColors.put(element, picker.getValue());
                renderMolecule();
            });
            colorsBox.getChildren().add(new HBox(8, new Label(element), picker));
        }
    }

    private Sphere createAtomSphere(Atom atom) {
        Sphere sphere = new Sphere(elementRadii.getOrDefault(atom.element(), 22.0));
        sphere.setTranslateX(atom.x() * SPACE_SCALE);
        sphere.setTranslateY(-atom.y() * SPACE_SCALE);
        sphere.setTranslateZ(atom.z() * SPACE_SCALE);
        sphere.setMaterial(new PhongMaterial(elementColors.getOrDefault(atom.element(), Color.LIGHTGRAY)));
        return sphere;
    }

    private Cylinder createBond(Atom first, Atom second) {
        Point3D origin = toPoint(first);
        Point3D target = toPoint(second);

        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();
        if (height < 1e-6) {
            return new Cylinder(8, 1);
        }
        Point3D midpoint = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ());
        Point3D axisOfRotation = diff.crossProduct(yAxis);
        if (axisOfRotation.magnitude() < 1e-6) {
            axisOfRotation = new Point3D(1, 0, 0);
        }
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder bond = new Cylinder(8, height);
        bond.setMaterial(new PhongMaterial(Color.rgb(210, 210, 210)));
        bond.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
        return bond;
    }

    private Point3D toPoint(Atom atom) {
        return new Point3D(
                atom.x() * SPACE_SCALE,
                -atom.y() * SPACE_SCALE,
                atom.z() * SPACE_SCALE
        );
    }

    private double distance(Atom first, Atom second) {
        double dx = first.x() - second.x();
        double dy = first.y() - second.y();
        double dz = first.z() - second.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private Color defaultColor(String element) {
        return switch (element.toUpperCase()) {
            case "H" -> Color.WHITE;
            case "C" -> Color.DARKGRAY;
            case "N" -> Color.DODGERBLUE;
            case "O" -> Color.CRIMSON;
            case "S" -> Color.GOLD;
            case "P" -> Color.ORANGE;
            default -> Color.LIGHTGRAY;
        };
    }

    private double defaultRadius(String element) {
        return switch (element.toUpperCase()) {
            case "H" -> 13;
            case "C" -> 20;
            case "N" -> 20;
            case "O" -> 19;
            case "S" -> 24;
            case "P" -> 23;
            default -> 18;
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
