package sem2_1.task3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sem2_1.common.SnapshotSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Task3App extends Application {
    private final List<EditablePrimitive> primitives = new ArrayList<>();

    private final ColorPicker strokeColorPicker = new ColorPicker(Color.DARKBLUE);
    private final ColorPicker fillColorPicker = new ColorPicker(Color.rgb(40, 120, 230, 0.25));
    private final TextField strokeWidthField = new TextField("3");
    private final ComboBox<LineStyle> lineStyleCombo = new ComboBox<>();
    private final TextField saveWidthField = new TextField("1000");
    private final TextField saveHeightField = new TextField("700");

    private EditablePrimitive currentPrimitive;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(6));

        Pane drawPane = new Pane();
        drawPane.setPrefSize(1000, 700);
        drawPane.setStyle("-fx-background-color: white; -fx-border-color: #c9c9c9;");

        ToggleGroup paletteGroup = new ToggleGroup();
        VBox palette = createPalette(paletteGroup);
        VBox controls = createControlPanel();
        MenuBar menuBar = createMenu(stage, drawPane);

        drawPane.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.SECONDARY) {
                drawPane.requestFocus();
                return;
            }
            ToggleButton selected = (ToggleButton) paletteGroup.getSelectedToggle();
            if (selected == null) {
                return;
            }
            DrawType type = (DrawType) selected.getUserData();
            EditablePrimitive primitive = createPrimitive(type, event.getX(), event.getY());
            primitive.applyStyle(
                    strokeColorPicker.getValue(),
                    fillColorPicker.getValue(),
                    readStrokeWidth(),
                    lineStyleCombo.getValue()
            );
            drawPane.getChildren().addAll(primitive.shape, primitive.frame);
            primitives.add(primitive);
            setCurrentPrimitive(primitive);
            drawPane.requestFocus();
        });

        Scene scene = new Scene(root, 1280, 820);
        scene.setOnKeyPressed(event -> {
            if (currentPrimitive == null) {
                return;
            }
            switch (event.getCode()) {
                case LEFT -> currentPrimitive.x -= 10;
                case RIGHT -> currentPrimitive.x += 10;
                case UP -> currentPrimitive.y -= 10;
                case DOWN -> currentPrimitive.y += 10;
                case MINUS, SUBTRACT -> currentPrimitive.height = Math.max(20, currentPrimitive.height - 10);
                case EQUALS, ADD -> {
                    if (event.isShiftDown() || event.getCode() == KeyCode.ADD) {
                        currentPrimitive.height += 10;
                    }
                }
                case COMMA -> {
                    if (event.isShiftDown()) {
                        currentPrimitive.width = Math.max(20, currentPrimitive.width - 10);
                    }
                }
                case PERIOD -> {
                    if (event.isShiftDown()) {
                        currentPrimitive.width += 10;
                    }
                }
                default -> {
                    return;
                }
            }
            currentPrimitive.updateGeometry();
        });

        root.setTop(menuBar);
        root.setLeft(palette);
        root.setCenter(drawPane);
        root.setRight(controls);

        stage.setTitle("Task 3 - Graphic Editor");
        stage.setScene(scene);
        stage.show();
        drawPane.requestFocus();
    }

    private VBox createPalette(ToggleGroup group) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(8));
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().add(new Label("Палитра"));

        box.getChildren().addAll(
                createPaletteButton("Линия", "/task3/icons/line.png", DrawType.LINE, group),
                createPaletteButton("Прямоугольник", "/task3/icons/rectangle.png", DrawType.RECTANGLE, group),
                createPaletteButton("Эллипс", "/task3/icons/ellipse.png", DrawType.ELLIPSE, group),
                createPaletteButton("Окружность", "/task3/icons/circle.png", DrawType.CIRCLE, group)
        );
        return box;
    }

    private ToggleButton createPaletteButton(String text, String iconPath, DrawType type, ToggleGroup group) {
        ImageView icon = new ImageView(new Image(Task3App.class.getResourceAsStream(iconPath)));
        icon.setFitHeight(28);
        icon.setFitWidth(28);
        icon.setPreserveRatio(true);
        ToggleButton button = new ToggleButton(text, icon);
        button.setUserData(type);
        button.setToggleGroup(group);
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private VBox createControlPanel() {
        lineStyleCombo.getItems().setAll(LineStyle.values());
        lineStyleCombo.getSelectionModel().select(LineStyle.SOLID);

        VBox box = new VBox(8);
        box.setPadding(new Insets(8));
        box.setPrefWidth(260);
        box.getChildren().addAll(
                new Label("Панель управления"),
                new Label("Цвет границы"),
                strokeColorPicker,
                new Label("Толщина границы"),
                strokeWidthField,
                new Label("Тип линии"),
                lineStyleCombo,
                new Label("Цвет заливки"),
                fillColorPicker,
                new Region(),
                new Label("Размер сохранения (px)"),
                new HBox(6, new Label("Ширина"), saveWidthField),
                new HBox(6, new Label("Высота"), saveHeightField)
        );
        return box;
    }

    private MenuBar createMenu(Stage stage, Pane drawPane) {
        Menu file = new Menu("Файл");
        MenuItem save = new MenuItem("Сохранить...");
        save.setOnAction(event -> saveDrawing(stage, drawPane));
        MenuItem exit = new MenuItem("Выход");
        exit.setOnAction(event -> stage.close());
        file.getItems().addAll(save, exit);

        Menu help = new Menu("Помощь");
        MenuItem instruction = new MenuItem("Инструкция");
        instruction.setOnAction(event -> {
            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION,
                    "1) Выберите примитив слева.\n2) ПКМ по белой панели добавляет фигуру.\n"
                            + "3) Текущая фигура выделена рамкой.\n"
                            + "4) Стрелки перемещают, +/- меняют высоту, </> меняют ширину.\n"
                            + "5) Файл -> Сохранить сохраняет изображение.",
                    ButtonType.OK
            );
            alert.setHeaderText("Как использовать редактор");
            alert.showAndWait();
        });
        help.getItems().add(instruction);

        return new MenuBar(file, help);
    }

    private void saveDrawing(Stage stage, Pane drawPane) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Сохранить рисунок");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );
        chooser.setInitialFileName("task3-editor.png");
        File file = chooser.showSaveDialog(stage);
        if (file == null) {
            return;
        }
        try {
            SnapshotSaver.save(drawPane, readInt(saveWidthField, (int) drawPane.getWidth()),
                    readInt(saveHeightField, (int) drawPane.getHeight()), file);
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, "Ошибка сохранения: " + exception.getMessage()).showAndWait();
        }
    }

    private EditablePrimitive createPrimitive(DrawType type, double centerX, double centerY) {
        EditablePrimitive primitive = new EditablePrimitive(type);
        primitive.width = 140;
        primitive.height = 95;
        primitive.x = centerX - primitive.width / 2.0;
        primitive.y = centerY - primitive.height / 2.0;
        primitive.updateGeometry();
        return primitive;
    }

    private void setCurrentPrimitive(EditablePrimitive primitive) {
        if (currentPrimitive != null) {
            currentPrimitive.frame.setVisible(false);
        }
        currentPrimitive = primitive;
        currentPrimitive.frame.setVisible(true);
    }

    private int readInt(TextField field, int defaultValue) {
        try {
            int value = Integer.parseInt(field.getText().trim());
            return Math.max(1, value);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    private double readStrokeWidth() {
        try {
            return Math.max(1.0, Double.parseDouble(strokeWidthField.getText().trim()));
        } catch (NumberFormatException exception) {
            return 3.0;
        }
    }

    private enum DrawType {
        LINE, RECTANGLE, ELLIPSE, CIRCLE
    }

    private enum LineStyle {
        SOLID("Сплошная"),
        DASHED("Пунктирная"),
        DOTTED("Точечная");

        private final String title;

        LineStyle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private static final class EditablePrimitive {
        private final DrawType type;
        private final Shape shape;
        private final Rectangle frame = new Rectangle();
        private double x;
        private double y;
        private double width;
        private double height;

        private EditablePrimitive(DrawType type) {
            this.type = type;
            this.shape = switch (type) {
                case LINE -> new Line();
                case RECTANGLE -> new Rectangle();
                case ELLIPSE -> new Ellipse();
                case CIRCLE -> new Circle();
            };
            frame.setFill(Color.rgb(70, 120, 255, 0.16));
            frame.setStroke(Color.rgb(30, 80, 200, 0.9));
            frame.getStrokeDashArray().setAll(10.0, 7.0);
            frame.setVisible(false);
        }

        private void applyStyle(Color strokeColor, Color fillColor, double strokeWidth, LineStyle style) {
            shape.setStroke(strokeColor);
            shape.setStrokeWidth(strokeWidth);
            shape.getStrokeDashArray().clear();
            if (style == LineStyle.DASHED) {
                shape.getStrokeDashArray().setAll(15.0, 10.0);
            } else if (style == LineStyle.DOTTED) {
                shape.getStrokeDashArray().setAll(3.0, 8.0);
            }
            if (type == DrawType.LINE) {
                shape.setFill(Color.TRANSPARENT);
            } else {
                shape.setFill(fillColor);
            }
        }

        private void updateGeometry() {
            width = Math.max(20, width);
            height = Math.max(20, height);

            switch (type) {
                case LINE -> {
                    Line line = (Line) shape;
                    line.setStartX(x);
                    line.setStartY(y);
                    line.setEndX(x + width);
                    line.setEndY(y + height);
                }
                case RECTANGLE -> {
                    Rectangle rectangle = (Rectangle) shape;
                    rectangle.setX(x);
                    rectangle.setY(y);
                    rectangle.setWidth(width);
                    rectangle.setHeight(height);
                }
                case ELLIPSE -> {
                    Ellipse ellipse = (Ellipse) shape;
                    ellipse.setCenterX(x + width / 2.0);
                    ellipse.setCenterY(y + height / 2.0);
                    ellipse.setRadiusX(width / 2.0);
                    ellipse.setRadiusY(height / 2.0);
                }
                case CIRCLE -> {
                    Circle circle = (Circle) shape;
                    circle.setCenterX(x + width / 2.0);
                    circle.setCenterY(y + height / 2.0);
                    circle.setRadius(Math.min(width, height) / 2.0);
                }
            }

            frame.setX(x);
            frame.setY(y);
            frame.setWidth(width);
            frame.setHeight(height);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
