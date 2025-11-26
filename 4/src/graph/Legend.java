package graph;

import java.util.ArrayList;
import java.util.List;

public class Legend {
    private List<Curve> curves;
    private String position; // "top-right", "bottom-left", etc.
    private boolean visible;
    
    public Legend(String position, boolean visible) {
        this.position = position;
        this.visible = visible;
        this.curves = new ArrayList<>();
    }
    
    public void addCurve(Curve curve) {
        curves.add(curve);
    }
    
    public String getPosition() { return position; }
    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
    
    public void draw() {
        if (visible && !curves.isEmpty()) {
            System.out.println("Легенда (" + position + "):");
            for (Curve curve : curves) {
                System.out.println("  ■ " + curve.getName() + " - " + curve.getColor());
            }
        }
    }
}