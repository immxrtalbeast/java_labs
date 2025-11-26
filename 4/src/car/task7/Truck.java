package car.task7;

import java.util.regex.Pattern;

public class Truck extends Car {
    private static final String LICENSE_PLATE_REGEX = 
        "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}RUS$";
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile(LICENSE_PLATE_REGEX);
    
    private double cargoCapacity;
    private boolean hasTrailer;
    
    public Truck(String brand, String color, Engine engine, int wheelsCount, 
                 double cargoCapacity, boolean hasTrailer) {
        super(brand, color, engine, wheelsCount);
        this.cargoCapacity = cargoCapacity;
        this.hasTrailer = hasTrailer;
    }
    
    public double getCargoCapacity() { 
        return cargoCapacity; 
    }
    
    public void setCargoCapacity(double capacity) { 
        this.cargoCapacity = capacity; 
    }
    
    public boolean hasTrailer() { 
        return hasTrailer; 
    }
    
    public void setHasTrailer(boolean hasTrailer) { 
        this.hasTrailer = hasTrailer; 
    }
    
    @Override
    protected boolean isValidLicensePlate(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               ", cargoCapacity=" + cargoCapacity + "т" +
               ", hasTrailer=" + hasTrailer + '}';
    }
}