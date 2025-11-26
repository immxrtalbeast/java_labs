package car.task5;

import java.util.regex.Pattern;


public class TruckCar extends Car {
    private static final String LICENSE_PLATE_REGEX = 
        "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}RUS$";
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile(LICENSE_PLATE_REGEX);
    
    private double cargoCapacity;
    
    public TruckCar(String brand, String color, Engine engine, int wheelsCount, double cargoCapacity) {
        super(brand, color, engine, wheelsCount);
        this.cargoCapacity = cargoCapacity;
    }
    
    public double getCargoCapacity() { return cargoCapacity; }
    public void setCargoCapacity(double capacity) { this.cargoCapacity = capacity; }
    
    @Override
    protected boolean isValidLicensePlate(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }
    
    @Override
    public String toString() {
        return super.toString().replace("}", ", cargoCapacity=" + cargoCapacity + "т}");
    }
}