package car.task7;

public class Engine {
    private final String serialNumber;    
    private final double power;          
    private final double volume;         
    private final double fuelConsumption; 
    private final FuelType fuelType;     
    private final int cylinders;         
    
    public Engine(String serialNumber, double power, double volume, 
                  double fuelConsumption, FuelType fuelType, int cylinders) {
        if (serialNumber == null || serialNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Серийный номер не может быть пустым");
        }
        if (power <= 0) {
            throw new IllegalArgumentException("Мощность должна быть положительной");
        }
        if (volume <= 0) {
            throw new IllegalArgumentException("Рабочий объем должен быть положительным");
        }
        if (fuelConsumption < 0) {
            throw new IllegalArgumentException("Расход топлива не может быть отрицательным");
        }
        if (cylinders <= 0) {
            throw new IllegalArgumentException("Количество цилиндров должно быть положительным");
        }
        
        this.serialNumber = serialNumber;
        this.power = power;
        this.volume = volume;
        this.fuelConsumption = fuelConsumption;
        this.fuelType = fuelType;
        this.cylinders = cylinders;
    }
    
    // Геттеры
    public String getSerialNumber() { return serialNumber; }
    public double getPower() { return power; }
    public double getVolume() { return volume; }
    public double getFuelConsumption() { return fuelConsumption; }
    public FuelType getFuelType() { return fuelType; }
    public int getCylinders() { return cylinders; }
    
    @Override
    public String toString() {
        return "Engine{" +
                "serialNumber='" + serialNumber + '\'' +
                ", power=" + power + " л.с." +
                ", volume=" + volume + " л" +
                ", fuelConsumption=" + fuelConsumption + " л/100км" +
                ", fuelType=" + fuelType +
                ", cylinders=" + cylinders +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Engine other = (Engine) obj;
        return serialNumber.equals(other.serialNumber);
    }
    
    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }
}