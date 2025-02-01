package ru.aston.ogurnoy_na.task1;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Класс для управления услугами парикмахерской и анализа данных.
 */
public class HairdressingSalonWork {
    private final List<HairdressingService> services;

    public HairdressingSalonWork() {
        this.services = initializeDemoServices();
    }

    /**
     * Инициализирует демонстрационный набор услуг
     */
    private List<HairdressingService> initializeDemoServices() {
        List<User> users = List.of(
                new User(62, "Иван", "Иванов", false, false),
                new User(30, "Мария", "Петрова", true, false),
                new User(44, "Сергей", "Смирнов", false, false),
                new User(64, "Елена", "Иванова", true, false),
                new User(24, "Светлана", "Иванова", true, true),
                new User(31, "Дмитрий", "Сидоров", false, false),
                new User(26, "Татьяна", "Петрова", true, true),
                new User(54, "Марина", "Иванова", true, false)
        );

        return List.of(
                new Haircut(1000, users.get(0), true, Haircut.HaircutType.MALE_BOX),
                new Haircut(1200, users.get(1), true, Haircut.HaircutType.FEMALE_PIXI),
                new Haircut(800, users.get(2), false, Haircut.HaircutType.FEMALE_BOB),
                new Haircut(900, users.get(3), true, Haircut.HaircutType.MALE_CROP),
                new Coloring(1400, users.get(4), false, Coloring.HairColor.BLACK),
                new Coloring(1600, users.get(5), true, Coloring.HairColor.BROWN),
                new Coloring(1500, users.get(6), false, Coloring.HairColor.BLOND),
                new Coloring(1300, users.get(7), true, Coloring.HairColor.WHITE)
        );
    }

    /**
     * Рассчитывает общую сумму выручки с учётом всех скидок
     */
    public double calculateTotalRevenue() {
        return services.stream()
                .mapToDouble(HairdressingService::getDiscountedPrice)
                .sum();
    }

    /**
     * Формирует отчёт об услугах, отсортированный по фамилиям клиентов
     */
    public String generateServicesReport() {
        return services.stream()
                .sorted(Comparator.comparing(s -> s.getUser().getSurname()))
                .map(HairdressingService::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return String.format("%s%n%nОбщая выручка: %.2f рублей",
                generateServicesReport(),
                calculateTotalRevenue());
    }
}