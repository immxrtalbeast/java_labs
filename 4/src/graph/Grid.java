package graph;

public class Grid {
    private double stepX;
    private double stepY;
    private boolean visible;
    
    public Grid(double stepX, double stepY, boolean visible) {
        this.stepX = stepX;
        this.stepY = stepY;
        this.visible = visible;
    }
    
    public double getStepX() { return stepX; }
    public double getStepY() { return stepY; }
    public boolean isVisible() { return visible; }
    
    public void setVisible(boolean visible) { this.visible = visible; }
    
    public void draw() {
        if (visible) {
            System.out.println("Координатная сетка: шаг по X=" + stepX + 
                             ", шаг по Y=" + stepY);
        }
    }
}