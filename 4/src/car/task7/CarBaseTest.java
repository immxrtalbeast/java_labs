package car.task7;

public class CarBaseTest {
    public static void main(String[] args) {
        // Создаем автобазу на 5 автомобилей
        CarBase base = new CarBase(5);
        
        // Создаем двигатели
        Engine engine1 = new Engine("ENG001", 150, 2.0, 8.5, FuelType.PETROL, 4);
        Engine engine2 = new Engine("ENG002", 200, 3.0, 12.0, FuelType.DIESEL, 6);
        Engine engine3 = new Engine("ENG003", 120, 1.6, 7.0, FuelType.PETROL, 4);
        
        // Создаем автомобили
        PassengerCar car1 = new PassengerCar("Toyota", "белый", engine1, 5, "седан");
        car1.setLicensePlate("А123ВС77RUS");
        
        Truck truck1 = new Truck("КАМАЗ", "синий", engine2, 6, 10.0, false);
        truck1.setLicensePlate("В456ЕК78RUS");
        
        PassengerCar car2 = new PassengerCar("Lada", "красный", engine3, 5, "хэтчбек");
        car2.setLicensePlate("С789МН99RUS");
        
        // Добавляем автомобили на базу
        System.out.println("=== Добавление автомобилей на базу ===");
        System.out.println("Добавлен автомобиль 1: " + base.addCar(car1));
        System.out.println("Добавлен грузовик: " + base.addCar(truck1));
        System.out.println("Добавлен автомобиль 2: " + base.addCar(car2));
        System.out.println("Текущее количество: " + base.getCurrentCount() + "/" + base.getMaxCapacity());
        System.out.println();
        
        // Показываем исправные автомобили на базе
        base.showWorkingCarsOnBase();
        System.out.println();
        
        // Отправляем исправный автомобиль в рейс
        System.out.println("=== Отправка в рейс ===");
        System.out.println("Отправка car1 в рейс: " + base.sendToTrip(car1));
        base.showCarsOnTrip();
        System.out.println();
        
        // Ломаем автомобиль и отправляем в ремонт
        System.out.println("=== Поломка и ремонт ===");
        base.breakCar(car2);
        System.out.println("Отправка сломанного car2 в ремонт: " + base.sendToRepair(car2));
        base.showBrokenCars();
        System.out.println();
        
        // Показываем текущее состояние
        System.out.println("=== Текущее состояние базы ===");
        base.showWorkingCarsOnBase();
        System.out.println();
        base.showCarsOnTrip();
        System.out.println();
        base.showBrokenCars();
        System.out.println();
        
        // Возвращаем автомобили
        System.out.println("=== Возврат автомобилей ===");
        System.out.println("Возврат car1 из рейса: " + base.returnFromTrip(car1));
        System.out.println("Возврат car2 из ремонта (отремонтирован): " + base.returnFromRepair(car2, true));
        System.out.println();
        
        // Финальное состояние
        System.out.println("=== Финальное состояние ===");
        base.showWorkingCarsOnBase();
        System.out.println();
        
        // Тест удаления
        System.out.println("=== Списание автомобиля ===");
        System.out.println("Списание truck1: " + base.removeCar(truck1));
        System.out.println("Текущее количество: " + base.getCurrentCount() + "/" + base.getMaxCapacity());
        base.showWorkingCarsOnBase();
    }
}