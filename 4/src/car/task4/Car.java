package car.task4;

import java.util.regex.Pattern;

public class Car {
    private String licensePlate;
    private final String brand;
    private final CarType type;
    private String color;
    private Engine engine;
    private final int wheelsCount;
    

    private static final String LICENSE_PLATE_REGEX = "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}RUS$";
    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile(LICENSE_PLATE_REGEX);
    

    public Car(String brand, CarType type, String color, Engine engine, int wheelsCount) {
        if (engine == null) {
            throw new IllegalArgumentException("Двигатель не может быть null");
        }
        
        this.brand = brand;
        this.type = type;
        this.color = color;
        this.engine = engine;
        this.wheelsCount = wheelsCount;
        this.licensePlate = null;
    }
    

    public Car(String brand, CarType type, String color, Engine engine, int wheelsCount, String licensePlate) {
        this(brand, type, color, engine, wheelsCount);
        setLicensePlate(licensePlate);
    }
    

    public String getLicensePlate() { return licensePlate; }
    public String getBrand() { return brand; }
    public CarType getType() { return type; }
    public String getColor() { return color; }
    public Engine getEngine() { return engine; }
    public int getWheelsCount() { return wheelsCount; }
    
    public void setColor(String color) { 
        this.color = color; 
    }
    
    public void setEngine(Engine engine) { 
        if (engine == null) {
            throw new IllegalArgumentException("Двигатель не может быть null");
        }
        this.engine = engine; 
    }
    
    public boolean setLicensePlate(String licensePlate) {
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
    

    private boolean isValidLicensePlate(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }
    
    @Override
    public String toString() {
        return "Car{" +
                "licensePlate='" + (licensePlate != null ? licensePlate : "отсутствует") + '\'' +
                ", brand='" + brand + '\'' +
                ", type=" + type +
                ", color='" + color + '\'' +
                ", engine=" + engine +
                ", wheelsCount=" + wheelsCount +
                '}';
    }
}