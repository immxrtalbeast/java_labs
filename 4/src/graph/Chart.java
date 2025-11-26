package graph;

import java.util.ArrayList;
import java.util.List;

public class Chart {
    private String title;
    private Axis xAxis;
    private Axis yAxis;
    private Grid grid;
    private Legend legend;
    private List<Curve> curves;
    private int width;
    private int height;
    
    public Chart(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.curves = new ArrayList<>();
        this.legend = new Legend("top-right", true);
    }
    
    public void setXAxis(Axis xAxis) { 
        this.xAxis = xAxis; 
    }
    
    public void setYAxis(Axis yAxis) { 
        this.yAxis = yAxis; 
    }
    
    public void setGrid(Grid grid) { 
        this.grid = grid; 
    }
    
    public void addCurve(Curve curve) {
        curves.add(curve);
        legend.addCurve(curve);
    }
    
    public String getTitle() { return title; }
    public Legend getLegend() { return legend; }
    
    public void draw() {
        System.out.println("=".repeat(50));
        System.out.println("ГРАФИК: " + title);
        System.out.println("Размер: " + width + "x" + height + " пикселей");
        System.out.println("=".repeat(50));
        
        if (xAxis != null) xAxis.draw();
        if (yAxis != null) yAxis.draw();
        if (grid != null) grid.draw();
        
        System.out.println("\nКривые на графике:");
        for (Curve curve : curves) {
            curve.draw();
            System.out.println();
        }
        
        legend.draw();
        System.out.println("=".repeat(50));
    }
}