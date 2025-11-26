package graph;

import java.util.ArrayList;
import java.util.List;

public class Curve {
    private String name;
    private String color;
    private List<Point> points;
    private String lineStyle; // "solid", "dashed", "dotted"
    
    public Curve(String name, String color, String lineStyle) {
        this.name = name;
        this.color = color;
        this.lineStyle = lineStyle;
        this.points = new ArrayList<>();
    }
    
    public void addPoint(Point point) {
        points.add(point);
    }
    
    public void addPoint(double x, double y) {
        points.add(new Point(x, y));
    }
    
    public String getName() { return name; }
    public String getColor() { return color; }
    public String getLineStyle() { return lineStyle; }
    public List<Point> getPoints() { return points; }
    
    public void draw() {
        System.out.println("Кривая '" + name + "' (цвет: " + color + 
                         ", стиль: " + lineStyle + "):");
        System.out.println("  Точки: " + points.size() + " шт.");
        for (int i = 0; i < Math.min(3, points.size()); i++) {
            System.out.println("    " + points.get(i));
        }
        if (points.size() > 3) {
            System.out.println("    ... и еще " + (points.size() - 3) + " точек");
        }
    }
}