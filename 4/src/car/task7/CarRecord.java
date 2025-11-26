package car.task7;

public class CarRecord {
    private final Car car;
    private CarStatus status;
    private boolean isWorking; // исправен/неисправен
    
    public CarRecord(Car car) {
        this.car = car;
        this.status = CarStatus.ON_BASE;
        this.isWorking = true; // по умолчанию исправен
    }
    
    public Car getCar() {
        return car;
    }
    
    public CarStatus getStatus() {
        return status;
    }
    
    public void setStatus(CarStatus status) {
        this.status = status;
    }
    
    public boolean isWorking() {
        return isWorking;
    }
    
    public void setWorking(boolean working) {
        this.isWorking = working;
    }
    
    @Override
    public String toString() {
        return car.getBaseInfo() + " - " + 
               (isWorking ? "исправен" : "неисправен") + 
               " (" + getStatusText() + ")";
    }
    
    private String getStatusText() {
        switch (status) {
            case ON_BASE: return "на базе";
            case ON_TRIP: return "в рейсе";
            case IN_REPAIR: return "в ремонте";
            default: return "неизвестно";
        }
    }
}