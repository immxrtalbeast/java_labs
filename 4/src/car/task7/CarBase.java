package car.task7;

public class CarBase {
    private final CarRecord[] cars;
    private final int maxCapacity;
    private int currentCount;
    
    public CarBase(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Максимальная вместимость должна быть положительной");
        }
        this.maxCapacity = maxCapacity;
        this.cars = new CarRecord[maxCapacity];
        this.currentCount = 0;
    }
    
    public boolean addCar(Car car) {
        if (currentCount >= maxCapacity) {
            return false;
        }
        cars[currentCount] = new CarRecord(car);
        currentCount++;
        return true;
    }
    
    public boolean removeCar(Car car) {
        for (int i = 0; i < currentCount; i++) {
            if (cars[i].getCar().equals(car)) {
                // Сдвигаем элементы влево
                for (int j = i; j < currentCount - 1; j++) {
                    cars[j] = cars[j + 1];
                }
                cars[currentCount - 1] = null;
                currentCount--;
                return true;
            }
        }
        return false;
    }
    
    public boolean sendToTrip(Car car) {
        CarRecord record = findCarRecord(car);
        if (record != null && record.getStatus() == CarStatus.ON_BASE && record.isWorking()) {
            record.setStatus(CarStatus.ON_TRIP);
            return true;
        }
        return false;
    }
    
    public boolean sendToRepair(Car car) {
        CarRecord record = findCarRecord(car);
        if (record != null && record.getStatus() == CarStatus.ON_BASE && !record.isWorking()) {
            record.setStatus(CarStatus.IN_REPAIR);
            return true;
        }
        return false;
    }
    
    public boolean returnFromTrip(Car car) {
        CarRecord record = findCarRecord(car);
        if (record != null && record.getStatus() == CarStatus.ON_TRIP) {
            record.setStatus(CarStatus.ON_BASE);
            return true;
        }
        return false;
    }
    
    public boolean returnFromRepair(Car car, boolean isFixed) {
        CarRecord record = findCarRecord(car);
        if (record != null && record.getStatus() == CarStatus.IN_REPAIR) {
            record.setStatus(CarStatus.ON_BASE);
            record.setWorking(isFixed);
            return true;
        }
        return false;
    }
    
    public void breakCar(Car car) {
        CarRecord record = findCarRecord(car);
        if (record != null) {
            record.setWorking(false);
        }
    }
    
    public void showWorkingCarsOnBase() {
        System.out.println("=== Исправные автомобили на базе ===");
        boolean found = false;
        for (int i = 0; i < currentCount; i++) {
            CarRecord record = cars[i];
            if (record.getStatus() == CarStatus.ON_BASE && record.isWorking()) {
                System.out.println(record);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Нет исправных автомобилей на базе");
        }
    }
    
    public void showCarsOnTrip() {
        System.out.println("=== Автомобили в рейсе ===");
        boolean found = false;
        for (int i = 0; i < currentCount; i++) {
            CarRecord record = cars[i];
            if (record.getStatus() == CarStatus.ON_TRIP) {
                System.out.println(record);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Нет автомобилей в рейсе");
        }
    }
    
    public void showBrokenCars() {
        System.out.println("=== Неисправные автомобили ===");
        boolean found = false;
        for (int i = 0; i < currentCount; i++) {
            CarRecord record = cars[i];
            if (!record.isWorking()) {
                System.out.println(record);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Нет неисправных автомобилей");
        }
    }
    
    private CarRecord findCarRecord(Car car) {
        for (int i = 0; i < currentCount; i++) {
            if (cars[i].getCar().equals(car)) {
                return cars[i];
            }
        }
        return null;
    }
    
    public int getCurrentCount() {
        return currentCount;
    }
    
    public int getMaxCapacity() {
        return maxCapacity;
    }
    
    public boolean isFull() {
        return currentCount >= maxCapacity;
    }
}