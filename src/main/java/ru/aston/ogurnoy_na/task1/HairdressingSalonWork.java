package ru.aston.ogurnoy_na.task1;

import ru.aston.ogurnoy_na.task1.enums.HairColor;
import ru.aston.ogurnoy_na.task1.enums.HaircutType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для управления услугами парикмахерской и анализа данных.
 */
public class HairdressingSalonWork {
    private static final String REPORT_HEADER = "Отчёт об услугах парикмахерской:\n";
    private static final String TOTAL_REVENUE_FORMAT = "\nОбщая выручка: %.2f рублей";
    private final List<HairdressingService> services;

    public HairdressingSalonWork() {
        this.services = initializeDemoServices();
    }

    /**
     * Инициализирует демонстрационный набор услуг.
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
                new Haircut(new BigDecimal("1000"), users.get(0), true, HaircutType.MALE_BOX),
                new Haircut(new BigDecimal("1200"), users.get(1), true, HaircutType.FEMALE_PIXI),
                new Haircut(new BigDecimal("800"), users.get(2), false, HaircutType.FEMALE_BOB),
                new Haircut(new BigDecimal("900"), users.get(3), true, HaircutType.MALE_CROP),
                new Coloring(new BigDecimal("1400"), users.get(4), false, HairColor.BLACK),
                new Coloring(new BigDecimal("1600"), users.get(5), true, HairColor.BROWN),
                new Coloring(new BigDecimal("1500"), users.get(6), false, HairColor.BLOND),
                new Coloring(new BigDecimal("1300"), users.get(7), true, HairColor.WHITE)
        );
    }

    /**
     * Рассчитывает общую сумму выручки с учётом всех скидок.
     */
    public BigDecimal calculateTotalRevenue() {
        if (services.isEmpty()) {
            return BigDecimal.ZERO; // Защита от пустого списка услуг
        }
        return services.stream()
                .map(HairdressingService::calculateFinalPrice) // Используем метод для получения цены со скидкой
                .reduce(BigDecimal.ZERO, BigDecimal::add) // Складываем все цены со скидкой
                .setScale(2, RoundingMode.HALF_UP); // Два знака после запятой
    }

    /**
     * Формирует отчёт об услугах, отсортированный по фамилиям клиентов.
     */
    public String generateServicesReport() {
        if (services.isEmpty()) {
            return "Нет доступных услуг."; // Защита от пустого списка услуг
        }
        return REPORT_HEADER + services.stream()
                .sorted(Comparator.comparing(s -> s.getUser().getSurname())) // Сортировка по фамилии
                .map(HairdressingService::toString) // Преобразование каждой услуги в строку
                .collect(Collectors.joining("\n")); // Объединение в одну строку
    }

    /**
     * Возвращает среднюю стоимость услуги.
     */
    public BigDecimal calculateAverageServicePrice() {
        if (services.isEmpty()) {
            return BigDecimal.ZERO; // Защита от пустого списка услуг
        }
        BigDecimal totalRevenue = calculateTotalRevenue();
        return totalRevenue.divide(BigDecimal.valueOf(services.size()), 2, RoundingMode.HALF_UP); // Два знака после запятой
    }

    /**
     * Фильтрует услуги по типу (например, только стрижки или окрашивания).
     */
    public List<HairdressingService> filterServicesByType(Class<? extends HairdressingService> serviceType) {
        return services.stream()
                .filter(serviceType::isInstance) // Фильтрация по типу
                .collect(Collectors.toList());
    }

    /**
     * Генерирует строковое представление объекта.
     */
    @Override
    public String toString() {
        return generateServicesReport() + String.format(TOTAL_REVENUE_FORMAT, calculateTotalRevenue());
    }
}