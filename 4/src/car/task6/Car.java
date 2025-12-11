package car.task6;


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
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Марка не может быть пустой");
        }
        
        this.brand = brand;
        this.color = color;
        this.engine = engine;
        this.wheelsCount = wheelsCount;
        this.licensePlate = null;
    }
    

    protected abstract boolean isValidLicensePlate(String licensePlate);
    
    public final String getLicensePlate() { 
        return licensePlate; 
    }
    
    public final String getBrand() { 
        return brand; 
    }
    
    public final String getColor() { 
        return color; 
    }
    
    public final Engine getEngine() { 
        return engine; 
    }
    
    public final int getWheelsCount() { 
        return wheelsCount; 
    }
    

    public final void setColor(String color) { 
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Цвет не может быть пустым");
        }
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
                ", wheelsCount=" + wheelsCount;
    }
    
 
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Car other = (Car) obj;
        return wheelsCount == other.wheelsCount &&
               brand.equals(other.brand) &&
               color.equals(other.color) &&
               engine.equals(other.engine) &&
               java.util.Objects.equals(licensePlate, other.licensePlate);
    }
    
 
    public final String getBaseInfo() {
        return "Автомобиль " + brand + " (" + getClass().getSimpleName() + "), цвет: " + color + ", колес: " + wheelsCount;
    }
}