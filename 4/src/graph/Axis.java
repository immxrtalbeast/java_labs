package graph;

public class Axis {
    private String label;
    private double minValue;
    private double maxValue;
    private String orientation; // "horizontal" или "vertical"
    
    public Axis(String label, double minValue, double maxValue, String orientation) {
        this.label = label;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.orientation = orientation;
    }
    
    public String getLabel() { return label; }
    public double getMinValue() { return minValue; }
    public double getMaxValue() { return maxValue; }
    public String getOrientation() { return orientation; }
    
    public void draw() {
        System.out.println("Ось " + orientation + ": " + label + " [" + minValue + " .. " + maxValue + "]");
        }
}