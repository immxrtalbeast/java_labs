package car.task5;

public abstract class Car {
    private String licensePlate;
    private final String brand;
    private String color;
    private Engine engine;
    private final int wheelsCount;
    
    protected Car(String brand, String color, Engine engine, int wheelsCount) {
        if (engine == null) {
            throw new IllegalArgumentException("Двигатель не может быть null");
        }
        
        this.brand = brand;
        this.color = color;
        this.engine = engine;
        this.wheelsCount = wheelsCount;
        this.licensePlate = null;
    }

    protected abstract boolean isValidLicensePlate(String licensePlate);
    

    public final String getLicensePlate() { return licensePlate; }
    public final String getBrand() { return brand; }
    public final String getColor() { return color; }
    public final Engine getEngine() { return engine; }
    public final int getWheelsCount() { return wheelsCount; }
    

    public final void setColor(String color) { 
        this.color = color; 
    }
    
    public final void setEngine(Engine engine) { 
        if (engine == null) {
            throw new IllegalArgumentException("Двигатель не может быть null");
        }
        this.engine = engine; 
    }
    
    public final boolean setLicensePlate(String licensePlate) {
        if (licensePlate == null) {
            this.licensePlate = null;
            return true;
        }
        
        if (isValidLicensePlate(licensePlate)) {
            this.licensePlate = licensePlate;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "licensePlate='" + (licensePlate != null ? licensePlate : "отсутствует") + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", engine=" + engine +
                ", wheelsCount=" + wheelsCount +
                '}';
    }
}