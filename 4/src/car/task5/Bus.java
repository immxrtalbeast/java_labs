package car.task5;

import java.util.regex.Pattern;

public final class Bus extends Car {
    private static final String LICENSE_PLATE_REGEX = 
        "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}RUS$";
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile(LICENSE_PLATE_REGEX);
    
    private int passengerCapacity;
    private boolean hasAirConditioning;
    
    public Bus(String brand, String color, Engine engine, int wheelsCount, 
               int passengerCapacity, boolean hasAirConditioning) {
        super(brand, color, engine, wheelsCount);
        this.passengerCapacity = passengerCapacity;
        this.hasAirConditioning = hasAirConditioning;
    }
    
    public int getPassengerCapacity() { return passengerCapacity; }
    public void setPassengerCapacity(int capacity) { this.passengerCapacity = capacity; }
    
    public boolean hasAirConditioning() { return hasAirConditioning; }
    public void setAirConditioning(boolean hasAirConditioning) { 
        this.hasAirConditioning = hasAirConditioning; 
    }
    
    @Override
    protected boolean isValidLicensePlate(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }
    
    @Override
    public String toString() {
        return super.toString().replace("}", 
            ", passengerCapacity=" + passengerCapacity + 
            ", hasAirConditioning=" + hasAirConditioning + '}');
    }
}