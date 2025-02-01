package ru.aston.ogurnoy_na.task1;

import java.util.Objects;

/**
 * Класс, представляющий услугу стрижки, наследующий базовые характеристики парикмахерских услуг.
 */
public class Haircut extends HairdressingService {
    private static final double PENSIONER_DISCOUNT = 0.1;
    private static final int PENSION_AGE = 60;

    /**
     * Типы доступных стрижек.
     */
    public enum HaircutType {
        MALE_BOX,
        MALE_CROP,
        FEMALE_PIXI,
        FEMALE_BOB
    }

    private final HaircutType haircutType;

    /**
     * Конструктор услуги стрижки.
     *
     * @param price стоимость услуги (должна быть неотрицательной)
     * @param user пользователь (не может быть null)
     * @param longHair признак длинных волос
     * @param haircutType тип стрижки (не может быть null)
     * @throws IllegalArgumentException если параметры не соответствуют ограничениям
     */
    public Haircut(double price,
                   User user,
                   boolean longHair,
                   HaircutType haircutType) {
        super(price, user, longHair);
        validateHaircutType(haircutType);
        this.haircutType = haircutType;
    }

    private void validateHaircutType(HaircutType type) {
        if (type == null) {
            throw new IllegalArgumentException("Тип стрижки не может быть null");
        }
    }

    /**
     * Возвращает тип стрижки.
     */
    public HaircutType getHaircutType() {
        return haircutType;
    }

    /**
     * Рассчитывает общую скидку с учётом возрастной скидки для пенсионеров.
     *
     * @return суммарная скидка
     */
    @Override
    public double calculateDiscount() {
        double discount = super.calculateDiscount();

        if (getUser().getAge() > PENSION_AGE) {
            discount += getPrice() * PENSIONER_DISCOUNT;
        }

        return Math.min(discount, getPrice()); // Защита от скидки больше цены
    }

    /**
     * Форматированное строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("%s, тип стрижки: %s",
                super.toString(),
                haircutType.toString().toLowerCase().replace('_', ' '));
    }

    /**
     * Проверка равенства объектов.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Haircut haircut = (Haircut) o;
        return haircutType == haircut.haircutType;
    }

    /**
     * Расчёт хеш-кода объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), haircutType);
    }
}