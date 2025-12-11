package car.task4;

public class CarEngineTest {
    public static void main(String[] args) {
        Engine engine1 = new Engine("SN123456", 150.0, 2.0, 8.5, FuelType.PETROL, 4);
        Engine engine2 = new Engine("SN789012", 350.0, 5.0, 15.0, FuelType.DIESEL, 6);
        Engine engine3 = new Engine("SN345678", 200.0, 3.0, 12.0, FuelType.PETROL, 6);
        
        System.out.println("=== ТЕСТИРОВАНИЕ ДВИГАТЕЛЕЙ ===");
        System.out.println("Двигатель 1: " + engine1);
        System.out.println("Двигатель 2: " + engine2);
        System.out.println("Двигатель 3: " + engine3);
        
        System.out.println("\n=== ТЕСТИРОВАНИЕ АВТОМОБИЛЕЙ С ДВИГАТЕЛЯМИ ===");
        
        Car car1 = new Car("Toyota", CarType.PASSENGER, "Красный", engine1, 4);
        System.out.println("Автомобиль 1: " + car1);
        
        Car car2 = new Car("Volvo", CarType.TRUCK, "Синий", engine2, 6, "А123ВС777RUS");
        System.out.println("Автомобиль 2: " + car2);
        
        Car bus = new Car("Mercedes", CarType.BUS, "Белый", engine3, 6, "У789ХХ00RUS");
        System.out.println("Автобус: " + bus);
        
        System.out.println("\n=== ТЕСТИРОВАНИЕ ИЗМЕНЕНИЙ ===");
        
        car1.setColor("Зеленый");
        System.out.println("После изменения цвета: " + car1);
        
        Engine newEngine = new Engine("SN999999", 180.0, 2.2, 7.8, FuelType.PETROL, 4);
        car1.setEngine(newEngine);
        System.out.println("После замены двигателя: " + car1);
        
        boolean success = car1.setLicensePlate("М456ОР123RUS");
        System.out.println("Установка знака: " + (success ? "успешно" : "не удалось"));
        System.out.println("После установки знака: " + car1);
        
        success = car1.setLicensePlate("НЕПРАВИЛЬНЫЙ123");
        System.out.println("Установка невалидного знака: " + (success ? "успешно" : "не удалось"));
        
        System.out.println("\n=== ТЕСТИРОВАНИЕ ЭЛЕКТРИЧЕСКОГО АВТОМОБИЛЯ ===");
        Engine electricEngine = new Engine("SN-ELEC-001", 300.0, 0.0, 0.0, FuelType.ELECTRIC, 0);
        Car electricCar = new Car("Tesla", CarType.PASSENGER, "Черный", electricEngine, 4, "Е001КХ777RUS");
        System.out.println("Электромобиль: " + electricCar);
        
        System.out.println("\n=== ТЕСТИРОВАНИЕ ГИБРИДНОГО АВТОМОБИЛЯ ===");
        Engine hybridEngine = new Engine("SN-PETROL-002", 200.0, 1.8, 5.5, FuelType.PETROL, 4);
        Car hybridCar = new Car("Toyota Prius", CarType.PASSENGER, "Серебристый", hybridEngine, 4);
        System.out.println("Гибридный автомобиль: " + hybridCar);
    }
}