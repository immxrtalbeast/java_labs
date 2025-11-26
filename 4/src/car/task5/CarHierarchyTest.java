package car.task5;

public class CarHierarchyTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТИРОВАНИЕ ИЕРАРХИИ КЛАССОВ АВТОМОБИЛЕЙ ===");
        
        Engine petrolEngine = new Engine("SN-PETROL-001", 150.0, 2.0, 8.5, FuelType.PETROL, 4);
        Engine dieselEngine = new Engine("SN-DIESEL-001", 350.0, 5.0, 15.0, FuelType.DIESEL, 6);
        Engine busEngine = new Engine("SN-BUS-001", 250.0, 4.0, 20.0, FuelType.DIESEL, 6);
        Engine specialEngine = new Engine("SN-SPECIAL-001", 300.0, 4.5, 18.0, FuelType.DIESEL, 8);
        
        PassengerCar passengerCar = new PassengerCar("Toyota Camry", "Черный", petrolEngine, 5);
        passengerCar.setLicensePlate("А123ВС777RUS");
        System.out.println("Легковой автомобиль: " + passengerCar);
        
        TruckCar truck = new TruckCar("Volvo FH", "Синий", dieselEngine, 6, 20.0);
        truck.setLicensePlate("М456ОР123RUS");
        System.out.println("Грузовой автомобиль: " + truck);
        
        Bus bus = new Bus("Mercedes Sprinter", "Белый", busEngine, 6, 20, true);
        bus.setLicensePlate("У789ХХ000RUS");
        System.out.println("Автобус: " + bus);
        
        SpecialCar diplomaticCar = new SpecialCar("Audi A8", "Черный", specialEngine, 4, 
                                                 "Дипломатическая миссия", true);
        diplomaticCar.setLicensePlate("D001DD001RUS");
        System.out.println("Дипломатический автомобиль: " + diplomaticCar);
        
        SpecialCar fireTruck = new SpecialCar("MAN", "Красный", specialEngine, 6, 
                                             "Пожарная служба", true);
        fireTruck.setLicensePlate("П999ОФ777RUS");
        System.out.println("Пожарная машина: " + fireTruck);
        
        System.out.println("\n=== ТЕСТИРОВАНИЕ НЕВАЛИДНЫХ НОМЕРОВ ===");
        
        boolean success = passengerCar.setLicensePlate("INVALID123");
        System.out.println("Установка невалидного номера для легкового: " + 
                          (success ? "успешно" : "не удалось"));
        
        success = passengerCar.setLicensePlate("D001DD001RUS");
        System.out.println("Установка дипломатического номера для легкового: " + 
                          (success ? "успешно" : "не удалось"));
    }
}
