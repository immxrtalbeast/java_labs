package car.task1;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Car {
    private String licensePlate;  
    private final String brand;
    private final CarType type;
    private String color;   
    private double enginePower;
    private final int wheelsCount;

    private static final String LICENSE_PLATE_REGEX = 
        "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}RUS$";
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile(LICENSE_PLATE_REGEX);
    

    public Car(String brand, CarType type, String color, double enginePower, int wheelsCount) {
        this.brand = brand;
        this.type = type;
        this.color = color;
        this.enginePower = enginePower;
        this.wheelsCount = wheelsCount;
        this.licensePlate = null; 
    }

    public Car(String brand, CarType type, String color, double enginePower, int wheelsCount, String licensePlate) {
        this(brand, type, color, enginePower, wheelsCount);
        setLicensePlate(licensePlate); 
    }
    

    public String getLicensePlate() {
        return licensePlate;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public CarType getType() {
        return type;
    }
    
    public String getColor() {
        return color;
    }
    
    public double getEnginePower() {
        return enginePower;
    }
    
    public int getWheelsCount() {
        return wheelsCount;
    }
    

    public void setLicensePlate(String licensePlate) {
        if (licensePlate != null && !isValidLicensePlate(licensePlate)) {
            throw new IllegalArgumentException(
                "Неверный формат регистрационного знака: " + licensePlate);
        }
        this.licensePlate = licensePlate;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }
    

    private boolean isValidLicensePlate(String licensePlate) {
        if (licensePlate == null) {
            return true;
        }
        Matcher matcher = LICENSE_PLATE_PATTERN.matcher(licensePlate);
        return matcher.matches();
    }
    

    @Override
    public String toString() {
        return String.format(
            "Автомобиль: %s%n" +
            "Марка: %s%n" +
            "Вид: %s%n" +
            "Цвет: %s%n" +
            "Мощность двигателя: %.1f л.с.%n" +
            "Количество колес: %d%n" +
            "Регистрационный знак: %s",
            brand, brand, typeToString(type), color, enginePower, 
            wheelsCount, 
            licensePlate != null ? licensePlate : "отсутствует"
        );
    }

    private String typeToString(CarType type) {
        switch (type) {
            case PASSENGER: return "легковой";
            case TRUCK: return "грузовой";
            case BUS: return "автобус";
            default: return "неизвестный";
        }
    }
}