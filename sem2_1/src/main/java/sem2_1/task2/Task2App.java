package sem2_1.task2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.Random;

public class Task2App extends Application {
    private final Random random = new Random();

    private PrimitiveType primitiveType;
    private double x = 220;
    private double y = 170;
    private double width = 180;
    private double height = 130;

    private Shape primitive;
    private Rectangle frame;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setPadding(new Insets(8));
        root.setStyle("-fx-background-color: white;");
        root.setPrefSize(960, 720);

        primitiveType = PrimitiveType.values()[random.nextInt(PrimitiveType.values().length)];
        primitive = createPrimitive(primitiveType);
        frame = new Rectangle();
        frame.setStroke(Color.rgb(30, 70, 180, 0.85));
        frame.setFill(Color.rgb(60, 130, 255, 0.18));
        frame.getStrokeDashArray().setAll(10.0, 7.0);

        root.getChildren().addAll(frame, primitive);
        updatePrimitiveAndFrame();

        Scene scene = new Scene(root, 960, 720);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> x -= 10;
                case RIGHT -> x += 10;
                case UP -> y -= 10;
                case DOWN -> y += 10;
                case MINUS, SUBTRACT -> height = Math.max(20, height - 10);
                case EQUALS, ADD -> {
                    if (event.isShiftDown() || event.getCode() == KeyCode.ADD) {
                        height += 10;
                    }
                }
                case COMMA -> {
                    if (event.isShiftDown()) {
                        width = Math.max(20, width - 10);
                    }
                }
                case PERIOD -> {
                    if (event.isShiftDown()) {
                        width += 10;
                    }
                }
                default -> {
                    return;
                }
            }
            updatePrimitiveAndFrame();
        });
        scene.setOnMouseClicked(event -> root.requestFocus());

        stage.setTitle("Task 2 - Random Primitive (" + primitiveType.label + ")");
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    private Shape createPrimitive(PrimitiveType type) {
        return switch (type) {
            case LINE -> {
                Line line = new Line();
                line.setStroke(Color.DARKRED);
                line.setStrokeWidth(3);
                yield line;
            }
            case CIRCLE -> {
                Circle circle = new Circle();
                circle.setStroke(Color.DARKGREEN);
                circle.setFill(Color.rgb(80, 180, 80, 0.3));
                circle.setStrokeWidth(3);
                yield circle;
            }
            case ELLIPSE -> {
                Ellipse ellipse = new Ellipse();
                ellipse.setStroke(Color.DARKORANGE);
                ellipse.setFill(Color.rgb(255, 170, 0, 0.35));
                ellipse.setStrokeWidth(3);
                yield ellipse;
            }
            case RECTANGLE -> {
                Rectangle rectangle = new Rectangle();
                rectangle.setStroke(Color.DARKBLUE);
                rectangle.setFill(Color.rgb(30, 120, 230, 0.3));
                rectangle.setStrokeWidth(3);
                yield rectangle;
            }
        };
    }

    private void updatePrimitiveAndFrame() {
        switch (primitiveType) {
            case LINE -> {
                Line line = (Line) primitive;
                line.setStartX(x);
                line.setStartY(y);
                line.setEndX(x + width);
                line.setEndY(y + height);
            }
            case CIRCLE -> {
                Circle circle = (Circle) primitive;
                circle.setCenterX(x + width / 2.0);
                circle.setCenterY(y + height / 2.0);
                circle.setRadius(Math.max(10, Math.min(width, height) / 2.0));
            }
            case ELLIPSE -> {
                Ellipse ellipse = (Ellipse) primitive;
                ellipse.setCenterX(x + width / 2.0);
                ellipse.setCenterY(y + height / 2.0);
                ellipse.setRadiusX(Math.max(10, width / 2.0));
                ellipse.setRadiusY(Math.max(10, height / 2.0));
            }
            case RECTANGLE -> {
                Rectangle rectangle = (Rectangle) primitive;
                rectangle.setX(x);
                rectangle.setY(y);
                rectangle.setWidth(Math.max(20, width));
                rectangle.setHeight(Math.max(20, height));
            }
        }

        frame.setX(x);
        frame.setY(y);
        frame.setWidth(Math.max(20, width));
        frame.setHeight(Math.max(20, height));
    }

    private enum PrimitiveType {
        LINE("Линия"),
        CIRCLE("Окружность"),
        ELLIPSE("Эллипс"),
        RECTANGLE("Прямоугольник");

        private final String label;

        PrimitiveType(String label) {
            this.label = label;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
