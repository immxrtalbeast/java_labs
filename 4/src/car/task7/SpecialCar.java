package car.task7;

import java.util.regex.Pattern;

public class SpecialCar extends Car {
    private static final String LICENSE_PLATE_REGEX = 
        "^(?:[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}|D\\d{3}DD\\d{2,3})RUS$";
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile(LICENSE_PLATE_REGEX);
    
    private String specialPurpose;
    private boolean hasSpecialEquipment;
    private String organization;
    
    public SpecialCar(String brand, String color, Engine engine, int wheelsCount, 
                      String specialPurpose, boolean hasSpecialEquipment, 
                      String organization) {
        super(brand, color, engine, wheelsCount);
        this.specialPurpose = specialPurpose;
        this.hasSpecialEquipment = hasSpecialEquipment;
        this.organization = organization;
    }
    
    public String getSpecialPurpose() { 
        return specialPurpose; 
    }
    
    public void setSpecialPurpose(String purpose) { 
        this.specialPurpose = purpose; 
    }
    
    public boolean hasSpecialEquipment() { 
        return hasSpecialEquipment; 
    }
    
    public void setSpecialEquipment(boolean hasEquipment) { 
        this.hasSpecialEquipment = hasEquipment; 
    }
    
    public String getOrganization() { 
        return organization; 
    }
    
    public void setOrganization(String organization) { 
        this.organization = organization; 
    }
    
    @Override
    protected boolean isValidLicensePlate(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               ", specialPurpose='" + specialPurpose + '\'' +
               ", hasSpecialEquipment=" + hasSpecialEquipment +
               ", organization='" + organization + '\'' + '}';
    }
}