package car.task5;

import java.util.regex.Pattern;

public class PassengerCar extends Car {
    private static final String LICENSE_PLATE_REGEX = 
        "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}RUS$";
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile(LICENSE_PLATE_REGEX);
    
    private int passengerCapacity;
    
    public PassengerCar(String brand, String color, Engine engine, int passengerCapacity) {
        super(brand, color, engine, 4); 
        this.passengerCapacity = passengerCapacity;
    }
    
    public int getPassengerCapacity() { return passengerCapacity; }
    public void setPassengerCapacity(int capacity) { this.passengerCapacity = capacity; }
    
    @Override
    protected boolean isValidLicensePlate(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }
    
    @Override
    public String toString() {
        return super.toString().replace("}", ", passengerCapacity=" + passengerCapacity + '}');
    }
}