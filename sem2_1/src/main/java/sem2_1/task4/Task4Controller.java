package sem2_1.task4;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.List;

public class Task4Controller {
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private CheckBox sinCheck;
    @FXML
    private CheckBox cosCheck;
    @FXML
    private CheckBox squareCheck;
    @FXML
    private CheckBox cubeCheck;
    @FXML
    private CheckBox expCheck;
    @FXML
    private TextField xMinField;
    @FXML
    private TextField xMaxField;
    @FXML
    private TextField stepField;
    @FXML
    private TextField strokeWidthField;

    @FXML
    private void initialize() {
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);
        redrawChart();
    }

    @FXML
    private void redrawChart() {
        double xMin;
        double xMax;
        double step;
        double strokeWidth;
        try {
            xMin = Double.parseDouble(xMinField.getText().trim());
            xMax = Double.parseDouble(xMaxField.getText().trim());
            step = Double.parseDouble(stepField.getText().trim());
            strokeWidth = Math.max(1.0, Double.parseDouble(strokeWidthField.getText().trim()));
        } catch (NumberFormatException exception) {
            showError("Проверьте корректность числовых параметров.");
            return;
        }

        if (step <= 0 || xMax < xMin) {
            showError("Условия диапазона: step > 0 и xMax >= xMin.");
            return;
        }

        lineChart.getData().clear();
        addSeriesIfSelected("sin(x)", sinCheck.isSelected(), xMin, xMax, step, strokeWidth, Math::sin);
        addSeriesIfSelected("cos(x)", cosCheck.isSelected(), xMin, xMax, step, strokeWidth, Math::cos);
        addSeriesIfSelected("x^2", squareCheck.isSelected(), xMin, xMax, step, strokeWidth, x -> x * x);
        addSeriesIfSelected("x^3", cubeCheck.isSelected(), xMin, xMax, step, strokeWidth, x -> x * x * x);
        addSeriesIfSelected("exp(x)", expCheck.isSelected(), xMin, xMax, step, strokeWidth, Math::exp);
    }

    private void addSeriesIfSelected(
            String name,
            boolean selected,
            double xMin,
            double xMax,
            double step,
            double strokeWidth,
            java.util.function.DoubleUnaryOperator function
    ) {
        if (!selected) {
            return;
        }
        List<FunctionSeriesBuilder.Point> points = FunctionSeriesBuilder.buildPoints(function, xMin, xMax, step);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        for (FunctionSeriesBuilder.Point point : points) {
            series.getData().add(new XYChart.Data<>(point.x(), point.y()));
        }
        lineChart.getData().add(series);
        series.nodeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setStyle("-fx-stroke-width: " + strokeWidth + "px;");
            }
        });
        Platform.runLater(() -> {
            if (series.getNode() != null) {
                series.getNode().setStyle("-fx-stroke-width: " + strokeWidth + "px;");
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText("Ошибка параметров графика");
        alert.showAndWait();
    }
}
