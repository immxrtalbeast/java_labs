package sem2_1.task4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public final class FunctionSeriesBuilder {
    private static final int MAX_POINTS = 20_000;

    private FunctionSeriesBuilder() {
    }

    public static List<Point> buildPoints(DoubleUnaryOperator function, double xMin, double xMax, double step) {
        if (function == null || step <= 0 || xMax < xMin) {
            return Collections.emptyList();
        }

        List<Point> points = new ArrayList<>();
        double x = xMin;
        int guard = 0;
        while (x <= xMax + step / 2.0 && guard < MAX_POINTS) {
            double y = function.applyAsDouble(x);
            if (Double.isFinite(y)) {
                points.add(new Point(x, y));
            }
            x += step;
            guard++;
        }
        return points;
    }

    public record Point(double x, double y) {
    }
}
