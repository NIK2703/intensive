package ru.aston.ogurnoy_na.task1;

import ru.aston.ogurnoy_na.task1.enums.HaircutType;
import ru.aston.ogurnoy_na.task1.exceptions.InvalidHaircutException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Класс, представляющий услугу стрижки, наследующий базовые характеристики парикмахерских услуг.
 */
public class Haircut extends HairdressingService {
    private static final BigDecimal PENSIONER_DISCOUNT = BigDecimal.valueOf(0.1);
    private static final int PENSION_AGE = 60; // Возраст, с которого применяется скидка для пенсионеров
    private final HaircutType haircutType;

    /**
     * Конструктор услуги стрижки.
     *
     * @param price       стоимость услуги (должна быть неотрицательной)
     * @param user        пользователь (не может быть null)
     * @param longHair    признак длинных волос
     * @param haircutType тип стрижки (не может быть null)
     * @throws InvalidHaircutException если параметры не соответствуют ограничениям
     */
    public Haircut(BigDecimal price, User user, boolean longHair, HaircutType haircutType) {
        super(price.setScale(2, RoundingMode.HALF_UP), user, longHair); // Устанавливаем два знака после запятой
        if (haircutType == null) {
            throw new InvalidHaircutException("Тип стрижки не может быть null");
        }
        this.haircutType = haircutType;
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
    protected BigDecimal calculateDiscount() {
        BigDecimal discount = super.calculateDiscount(); // Базовая скидка из родительского класса

        // Дополнительная скидка для пенсионеров
        if (getUser().getAge() > PENSION_AGE) {
            BigDecimal pensionerDiscountAmount = getPrice().multiply(PENSIONER_DISCOUNT);
            discount = discount.add(pensionerDiscountAmount);
        }

        return discount.min(getPrice()).setScale(2, RoundingMode.HALF_UP); // Два знака после запятой
    }

    /**
     * Возвращает детализацию скидок.
     * Добавляет информацию о скидке для пенсионеров.
     */
    @Override
    public String getDiscountDetails() {
        StringBuilder details = new StringBuilder(super.getDiscountDetails());

        // Добавляем информацию о скидке для пенсионеров
        if (getUser().getAge() > PENSION_AGE) {
            if (!details.isEmpty()) details.append("; ");
            details.append(String.format("Скидка для пенсионеров: %.1f%%",
                    PENSIONER_DISCOUNT.multiply(BigDecimal.valueOf(100))));
        }

        return details.toString();
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
        if (!(o instanceof Haircut that)) return false;
        return super.equals(o) && haircutType == that.haircutType;
    }

    /**
     * Расчёт хеш-кода объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), haircutType);
    }
}