package graph;

public class ChartTest {
    public static void main(String[] args) {
        // Создаем график
        Chart chart = new Chart("Графики математических функций", 800, 600);
        
        // Настраиваем оси
        Axis xAxis = new Axis("X", -10, 10, "horizontal");
        Axis yAxis = new Axis("Y", -5, 5, "vertical");
        chart.setXAxis(xAxis);
        chart.setYAxis(yAxis);
        
        // Настраиваем сетку
        Grid grid = new Grid(1.0, 0.5, true);
        chart.setGrid(grid);
        
        // Создаем кривую sin(x)
        Curve sinCurve = new Curve("sin(x)", "красный", "solid");
        for (double x = -10; x <= 10; x += 0.5) {
            sinCurve.addPoint(x, Math.sin(x));
        }
        chart.addCurve(sinCurve);
        
        // Создаем кривую cos(x)
        Curve cosCurve = new Curve("cos(x)", "синий", "dashed");
        for (double x = -10; x <= 10; x += 0.5) {
            cosCurve.addPoint(x, Math.cos(x));
        }
        chart.addCurve(cosCurve);
        
        // Создаем параболу x²/4
        Curve parabolaCurve = new Curve("x²/4", "зеленый", "dotted");
        for (double x = -4; x <= 4; x += 0.2) {
            parabolaCurve.addPoint(x, x * x / 4);
        }
        chart.addCurve(parabolaCurve);
        
        // Отображаем график
        chart.draw();
    }
}