package car.task6;

import java.util.regex.Pattern;


public final class Bus extends Car {
    private static final String LICENSE_PLATE_REGEX = 
        "[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}RUS";
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile(LICENSE_PLATE_REGEX);
    
    private final int passengerCapacity;
    private boolean hasAirConditioning;
    private boolean hasWheelchairAccess;
    
    public Bus(String brand, String color, Engine engine, int wheelsCount, 
               int passengerCapacity, boolean hasAirConditioning, 
               boolean hasWheelchairAccess) {
        super(brand, color, engine, wheelsCount);
        this.passengerCapacity = passengerCapacity;
        this.hasAirConditioning = hasAirConditioning;
        this.hasWheelchairAccess = hasWheelchairAccess;
    }
    
    public final int getPassengerCapacity() { 
        return passengerCapacity; 
    }
    
    public boolean hasAirConditioning() { 
        return hasAirConditioning; 
    }
    
    public void setAirConditioning(boolean hasAirConditioning) { 
        this.hasAirConditioning = hasAirConditioning; 
    }
    
    public boolean hasWheelchairAccess() { 
        return hasWheelchairAccess; 
    }
    
    public void setWheelchairAccess(boolean hasWheelchairAccess) { 
        this.hasWheelchairAccess = hasWheelchairAccess; 
    }
    
    @Override
    protected final boolean isValidLicensePlate(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               ", passengerCapacity=" + passengerCapacity +
               ", hasAirConditioning=" + hasAirConditioning +
               ", hasWheelchairAccess=" + hasWheelchairAccess + '}';
    }
}
