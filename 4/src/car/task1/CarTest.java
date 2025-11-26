package car.task1;

import java.util.Scanner;

public class CarTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("ТЕСТИРОВАНИЕ КЛАССА CAR\n");
        

        System.out.println("1. Создание автомобилей:");
        

        Car car1 = new Car("Toyota", CarType.PASSENGER, "красный", 150.5, 4);
        System.out.println("Автомобиль 1 создан без регистрационного знака:");
        System.out.println(car1);
        System.out.println();
        

        Car car2 = new Car("Volvo", CarType.TRUCK, "синий", 350.0, 6, "А123ВС77RUS");
        System.out.println("Автомобиль 2 создан с регистрационным знаком:");
        System.out.println(car2);
        System.out.println();
        

        Car car3 = new Car("Mercedes", CarType.BUS, "белый", 200.0, 6, "Е456ТХ123RUS");
        System.out.println("Автомобиль 3 создан с регистрационным знаком:");
        System.out.println(car3);
        System.out.println();
        

        System.out.println("2. Тестирование изменения характеристик:");
        

        System.out.println("Изменяем цвет автомобиля 1:");
        car1.setColor("зеленый");
        System.out.println("Новый цвет: " + car1.getColor());
        

        System.out.println("Изменяем мощность двигателя автомобиля 1:");
        car1.setEnginePower(160.0);
        System.out.println("Новая мощность: " + car1.getEnginePower() + " л.с.");
        
        System.out.println("Добавляем регистрационный знак автомобилю 1:");
        car1.setLicensePlate("Х789ОР99RUS");
        System.out.println("Новый регистрационный знак: " + car1.getLicensePlate());
        
        System.out.println("\nОбновленная информация об автомобиле 1:");
        System.out.println(car1);
        System.out.println();
        
        System.out.println("3. Тестирование обработки ошибок:");
        
        try {
            System.out.println("Попытка установить неверный регистрационный знак:");
            car2.setLicensePlate("НЕПРАВИЛЬНЫЙ123RUS");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        

        System.out.println("\n4. Интерактивное тестирование:");
        
        System.out.println("Создаем новый автомобиль:");
        System.out.print("Введите марку: ");
        String brand = scanner.nextLine();
        
        System.out.print("Введите вид (1-легковой, 2-грузовой, 3-автобус): ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine();
        
        CarType type;
        switch (typeChoice) {
            case 1: type = CarType.PASSENGER; break;
            case 2: type = CarType.TRUCK; break;
            case 3: type = CarType.BUS; break;
            default: type = CarType.PASSENGER;
        }
        
        System.out.print("Введите цвет: ");
        String color = scanner.nextLine();
        
        System.out.print("Введите мощность двигателя: ");
        double power = scanner.nextDouble();
        
        System.out.print("Введите количество колес: ");
        int wheels = scanner.nextInt();
        scanner.nextLine(); 
        
        Car interactiveCar = new Car(brand, type, color, power, wheels);
        
        System.out.print("Хотите добавить регистрационный знак? (y/n): ");
        String answer = scanner.nextLine();
        
        if (answer.equalsIgnoreCase("y")) {
            System.out.print("Введите регистрационный знак: ");
            String plate = scanner.nextLine();
            
            try {
                interactiveCar.setLicensePlate(plate);
                System.out.println("Регистрационный знак успешно добавлен!");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Автомобиль создан без регистрационного знака.");
            }
        }
        
        System.out.println("\nСозданный автомобиль:");
        System.out.println(interactiveCar);
        
        scanner.close();
    }
}